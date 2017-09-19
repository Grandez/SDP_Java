package com.jgg.sdp.web.persister.rest;

import java.util.Date;

// import javax.ws.rs.*;
// import javax.ws.rs.core.*;

// @Path("/")
public class Persister {
/*
	@GET
	@Produces("text/html")
	public Response getStartingPage()
	{
		String output = "<h1>Hello World!<h1>" +
				"<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>";
		return Response.status(200).entity(output).build();
	}
	*/
}
/*	
@POST
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_PLAIN)
public String postZip(InputStream is) throws IOException, SAXException {
	    byte[] buffer = new byte[1024];
        String line = "";
        StringBuilder sb = new  StringBuilder();
        try {
        	int rc = is.read(buffer); 
        	while (rc == 1024) {
        		sb.append(buffer);
        	}
        	for (int i = 0; i < rc; i++) {
        		sb.append(buffer[i]);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        return "0";
    }
   
}
*/
/*
	java.nio.file.Path path = Files.createTempFile(null, null);
	FileOutputStream fos = new FileOutputStream(path.toFile());
	ByteStreams.copy(is, fos);
	fos.close();
	is.close();

	TikaConfig config = TikaConfig.getDefaultConfig();
	Detector detector = config.getDetector();

	TikaInputStream stream = TikaInputStream.get(path.toFile());

	Metadata metadata = new Metadata();
	metadata.add(Metadata.RESOURCE_NAME_KEY, path.toString());
	org.apache.tika.mime.MediaType mediaType = detector.detect(stream,
			metadata);

	return mediaType.toString() + "   --   " + path.toString();

}

}
*/
/*
@Path("/input")
public class Input {
    //DAO instance to have connection to the database.
    //Not used yet.
    //private DAO dao = new DAO();

    @PUT
    @Consumes(MediaType.TEXT_XML)
    @Path("/result")
    public void putIntoDAO(InputStream xml) {
        String line = "";
        StringBuilder sb = new  StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(xml));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }
}
*/

/* Cliente
//Connect to the web server endpoint
URL serverUrl =
 new URL("http://localhost/test/GetPostRequest.php");
HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

String boundaryString = "----SomeRandomText";
String fileUrl = "/logs/20150208.log";
File logFileToUpload = new File(fileUrl);

//Indicate that we want to write to the HTTP request body
urlConnection.setDoOutput(true);
urlConnection.setRequestMethod("POST");
urlConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);

//Indicate that we want to write some data as the HTTP request body
urlConnection.setDoOutput(true);

OutputStream outputStreamToRequestBody = urlConnection.getOutputStream();
BufferedWriter httpRequestBodyWriter =
 new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));

//Include value from the myFileDescription text area in the post data
httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
httpRequestBodyWriter.write("Content-Disposition: form-data; name=\"myFileDescription\"");
httpRequestBodyWriter.write("\n\n");
httpRequestBodyWriter.write("Log file for 20150208");

//Include the section to describe the file
httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
httpRequestBodyWriter.write("Content-Disposition: form-data;"
     + "name=\"myFile\";"
     + "filename=\""+ logFileToUpload.getName() +"\""
     + "\nContent-Type: text/plain\n\n");
httpRequestBodyWriter.flush();

//Write the actual file contents
FileInputStream inputStreamToLogFile = new FileInputStream(logFileToUpload);

int bytesRead;
byte[] dataBuffer = new byte[1024];
while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
 outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
}

outputStreamToRequestBody.flush();

//Mark the end of the multipart http request
httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
httpRequestBodyWriter.flush();

//Close the streams
outputStreamToRequestBody.close();
httpRequestBodyWriter.close();

*/