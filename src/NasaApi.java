import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NasaApi
{

    private URL url;
    private HttpURLConnection con;


    public NasaApi(String api, String table, String columns, String order, String format) throws MalformedURLException
    {

        // set api endpoint
        if(api.equals("exoplanets"))
        {
            this.url = new URL("https://exoplanetarchive.ipac.caltech.edu/cgi-bin/nstedAPI/nph-nstedAPI?");
        }
        else
        {
            throw new MalformedURLException("API not found");
        }

        // add columns to return if columns are specified
        if(!columns.equals(""))
        {
            this.url = new URL(this.url + "&select=" + columns);
        }

        // add order if order is specified
        if(!order.equals(""))
        {
            this.url = new URL(this.url + "&order=" + order);
        }

        // add format if format is specified
        if(!format.equals(""))
        {
            this.url = new URL(this.url + "&format" + format);
        }

        // TODO reduce try catch statements
        // open connection
        try {
            this.con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set request method
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        

    }

}
