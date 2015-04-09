/**
 * Jyoti Salitra
 * UTA ID: ********
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 5
 * Date: 11/23/2014
 */

package edu.uta.cse.gcs;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Uploads files from a form to the Google Cloud Storage using BlobStore API
 * References:
 * 1. https://cloud.google.com/appengine/docs/java/blobstore/
 */
public class GCSUploader extends HttpServlet {

	private static final long serialVersionUID = 1335443421670403153L;

	//create a instance of BlobstoreService using its factory method 
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@SuppressWarnings("deprecation")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		//get all uploaded blobs from the Google Cloud Storage
		Map<String, BlobKey> blobMap = blobstoreService.getUploadedBlobs(req);
		//get a blob specified by the key myFile. 
		BlobKey blobKey = blobMap.get("myFile");

		//if this blob is not found, redirect to home page
		if (blobKey == null) {
			res.sendRedirect("/");
		} else {
			//otherwise, redirect to GCSDownloader
			res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
		}
	}
}
