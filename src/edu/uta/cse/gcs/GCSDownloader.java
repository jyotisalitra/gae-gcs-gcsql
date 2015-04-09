/**
 * Jyoti Salitra
 * UTA ID: ********
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 5
 * Date: 11/23/2014
 */

package edu.uta.cse.gcs;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Downloads a file from the Google Cloud Storage using BlobStore API
 * References:
 * 1. https://cloud.google.com/appengine/docs/java/blobstore/
 */
public class GCSDownloader extends HttpServlet {

	private static final long serialVersionUID = -8462826133376197249L;
	//create a instance of BlobstoreService using its factory method 
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		//downloads blob specified by request parameter blob-key
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		blobstoreService.serve(blobKey, res);
	}
}
