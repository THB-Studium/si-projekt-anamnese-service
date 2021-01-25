package thb.siprojektanamneseservice.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thb.siprojektanamneseservice.service.authencation.Credentials;

public class UrlUtils {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(UrlUtils.class);

    /**
     * Extract user name from Authorization header
     *
     * @param authorization
     *            Authorization header
     * @return credentials
     */
    public static Credentials extractCredentialsFromBasicAuthorization(String authorization) {
        if (authorization != null && authorization.startsWith("Basic")) {

            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.decodeBase64(base64Credentials));

            // credentials = username:password
            final String[] values = credentials.split(":", 2);

            return new Credentials(values[0], values[1]);
        } else {
            return null;
        }
    }
}
