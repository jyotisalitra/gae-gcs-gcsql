<%--
 * Jyoti Salitra
 * UTA ID: 1001055011
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 5
 * Date: 11/23/2014
 */
 
 /**
 * An HTML form that uploads a selected file from user's system to Google Cloud Storage 
 * References:
 * 1. https://cloud.google.com/appengine/docs/java/blobstore/
--%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.UploadOptions" %>
<%
	//create a instance of BlobstoreService using its factory method 
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	//generate an uploadUrl for this file so that it gets uploaded to the Google Cloud Storage bucket cse6331gap.appspot.com
	String uploadUrl = blobstoreService.createUploadUrl("/upload", UploadOptions.Builder.withGoogleStorageBucketName("cse6331gap.appspot.com"));
%>

<html>
    <head>
    	<meta charset="utf-8">
        <title>Google Cloud Storage | Upload</title>
    </head>
    <body>
        <form action="<%=uploadUrl%>" method="post" enctype="multipart/form-data">
            <input type="text" name="foo">
            <input type="file" name="myFile">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>