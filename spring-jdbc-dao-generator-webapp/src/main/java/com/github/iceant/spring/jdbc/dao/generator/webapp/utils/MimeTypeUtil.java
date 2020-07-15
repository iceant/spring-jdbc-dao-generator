package com.github.iceant.spring.jdbc.dao.generator.webapp.utils;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MimeTypeUtil {
    /* The default MIME type */
    public static final String DEFAULT_MIMETYPE = "application/octet-stream";

    private static MimeTypeUtil mimetypes = null;

    private HashMap<String, String> extensionToMimetypeMap = new HashMap<String, String>();

    private MimeTypeUtil() {
    }

    public synchronized static MimeTypeUtil getInstance() {
        if (mimetypes != null)
            return mimetypes;

        mimetypes = new MimeTypeUtil();
        InputStream is = mimetypes.getClass().getResourceAsStream("/mime.types");
        if (is != null) {
            try {
                mimetypes.loadMimetypes(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        }
        return mimetypes;
    }

    public void loadMimetypes(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        while ((line = br.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("#") || line.length() == 0) {
                // Ignore comments and empty lines.
            } else {
                StringTokenizer st = new StringTokenizer(line, " \t");
                if (st.countTokens() > 1) {
                    String extension = st.nextToken();
                    if (st.hasMoreTokens()) {
                        String mimetype = st.nextToken();
                        extensionToMimetypeMap.put(extension.toLowerCase(), mimetype);
                    }
                }
            }
        }
    }

    public String getMimetype(String fileName) {
        String mimeType = getMimetypeByExt(fileName);
        if (mimeType != null) {
            return mimeType;
        }
        if(MediaTypeFactory.getMediaType(fileName).isPresent()){
            MediaType mediaType = MediaTypeFactory.getMediaType(fileName).get();
            return mediaType.toString();
        }
        return DEFAULT_MIMETYPE;
    }

    public String getMimetype(File file) {
        return getMimetype(file.getName());
    }

    public String getMimetype(File file, String key) {
        return getMimetype(file.getName(), key);
    }

    public String getMimetype(String primaryObject, String secondaryObject) {
        String mimeType = getMimetypeByExt(primaryObject);
        if (mimeType != null) {
            return mimeType;
        }
        if(MediaTypeFactory.getMediaType(primaryObject).isPresent()){
            MediaType mediaType = MediaTypeFactory.getMediaType(primaryObject).get();
            return mediaType.toString();
        }


        mimeType = getMimetypeByExt(secondaryObject);
        if (mimeType != null) {
            return mimeType;
        }

        if(MediaTypeFactory.getMediaType(secondaryObject).isPresent()){
            MediaType mediaType = MediaTypeFactory.getMediaType(secondaryObject).get();
            return mediaType.toString();
        }

        return DEFAULT_MIMETYPE;
    }

    private String getMimetypeByExt(String fileName) {
        int lastPeriodIndex = fileName.lastIndexOf(".");
        if (lastPeriodIndex > 0 && lastPeriodIndex + 1 < fileName.length()) {
            String ext = fileName.substring(lastPeriodIndex + 1).toLowerCase();
            if (extensionToMimetypeMap.keySet().contains(ext)) {
                return (String) extensionToMimetypeMap.get(ext);
            }
        }
        return null;
    }

}
