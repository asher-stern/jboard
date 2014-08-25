package org.jboard.jboard.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * A subclass of java.util.ResourceBundle.Control which is able to load properties file encoded
 * in UTF8.
 *
 * @author Asher Stern
 * Date: Aug 20, 2014
 *
 */
public class UTF8RsourceBundleControl extends ResourceBundle.Control
{
	public static final UTF8RsourceBundleControl INSTANCE = new UTF8RsourceBundleControl();
	
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format,
			ClassLoader loader, boolean reload)
					throws IllegalAccessException, InstantiationException, IOException {
		String bundleName = toBundleName(baseName, locale);
		ResourceBundle bundle = null;
		if (!(format.equals("java.properties"))) {
			return super.newBundle(baseName, locale, format, loader, reload);
		} else if (format.equals("java.properties")) {
			try
			{
				final String resourceName = toResourceName(bundleName, "properties");
				final ClassLoader classLoader = loader;
				final boolean reloadFlag = reload;
				InputStream stream = null;
				try {
					stream = AccessController.doPrivileged(
							new PrivilegedExceptionAction<InputStream>() {
								public InputStream run() throws IOException {
									InputStream is = null;
									if (reloadFlag) {
										URL url = classLoader.getResource(resourceName);
										if (url != null) {
											URLConnection connection = url.openConnection();
											if (connection != null) {
												// Disable caches to get fresh data for
												// reloading.
												connection.setUseCaches(false);
												is = connection.getInputStream();
											}
										}
									} else {
										is = classLoader.getResourceAsStream(resourceName);
									}
									return is;
								}
							});
				} catch (PrivilegedActionException e) {
					throw (IOException) e.getException();
				}
				if (stream != null) {
					try {
						bundle = new PropertyResourceBundle(new InputStreamReader(stream,"UTF-8"));
					} finally {
						stream.close();
					}
				}
			}
			catch(Throwable t)
			{
				try
				{
					return super.newBundle(baseName, locale, format, loader, reload);
				}
				catch(Exception tt)
				{
					throw new RuntimeException("Failed to load resource due to: "+t.getClass().getName()+": "+ t.getMessage()+"\n"
							+ "Tried to load by "+ResourceBundle.Control.class.getSimpleName()+" but failed due to: "+tt.getMessage(),
							tt);

				}
			}
		}
		return bundle;
	}

}
