import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;

/*
* first code
* fail
*/
public class TakeDataFile {
	public static void main(String[] args) {
		(new TakeDataFile()).download();
	}
	public boolean download() {
		HttpURLConnection conn = getConnection(makeURL("https://data.gg.go.kr/portal/data/sheet/saveInfUsePurp.do"));
		open(conn);
		BufferedInputStream bins = getInputStream(conn);
		downloadFile(bins);
		return true;
	}
	public URL makeURL(String strurl) {
		URL url = null;
		try {
			url = new URL(strurl);
		} catch (MalformedURLException malformed) {
			throw new RuntimeException("URL is wrong.:(\n", malformed);
		}
		return url;
	}
	public HttpURLConnection getConnection(URL url) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException malformed) {
			throw new RuntimeException("URL is wrong.:(\n", malformed);
		} catch (IOException ioe) {
			throw new RuntimeException("Http connect is something wrong.\n", ioe);
		}
		return connection;
	}
	private void open(HttpURLConnection connection) {
		try {
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("infId", "3NPA52LBMO36CQEQ1GMY28894927");
			connection.setRequestProperty("dsUsePurpsCd", "U03");
			connection.connect();
		} catch (ProtocolException pe) {
			throw new RuntimeException("method wrong.:(\n", pe);
		} catch (IOException ioe) {
			throw new RuntimeException("connect fail.:(\n", ioe);
		}
	}
	private BufferedInputStream getInputStream(HttpURLConnection connection) {
		BufferedInputStream bins = null;
		try {
			bins = new BufferedInputStream(connection.getInputStream());
		} catch (IOException ioe) {
			throw new RuntimeException("BufferedInputStream is strange.\n", ioe);
		}
		return bins;
	}
	private void downloadFile(BufferedInputStream bins) {
		FileOutputStream bouts = null;
		try {
			bouts = new FileOutputStream("datafile");
			byte[] buff = new byte[256];
			while ((bins.read(buff)) != -1) {
				bouts.write(buff);
				bouts.flush();
			}
		} catch (IOException ioe) {
			throw new RuntimeException("downloadFile exception.\n", ioe);
		} finally {
			try {
				if (bouts != null) {
					bouts.close();
				}
			} catch (IOException ioe) {
				throw new RuntimeException("close exception.\n", ioe);
			}
			try {
				if (bins != null) {
					bins.close();
				}
			} catch (IOException ioe) {
				throw new RuntimeException("close exception.\n", ioe);
			}
		}
	}
}

