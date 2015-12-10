package com.adinaandsari.virtuallibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

/**
 * Created by adina_000 on 08-Dec-15.
 */
public class myAsyncTask extends AsyncTask<Void, Void, Void>
{

    private ProgressDialog progressDialog;
    private Activity myAct;
    private Exception exceptionThrown;
    private myFunc back;
    private myFunc post;
    private String message;

    public myAsyncTask(Activity activity, ProgressDialog progress, myFunc b, myFunc p,
                       String str)
    {
        super();
        myAct = activity;
        progressDialog = progress;
        back = b;
        post = p;
        message = str;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        try
        {
            back.run();
        }
        catch (Exception e)
        {
            exceptionThrown = e;

            if (e.getMessage().contains("Read timed out"))
            {
                exceptionThrown = new Exception("No response from server. Try again.");
            }
            else if (e.getMessage().contains("@@@"))
            {
                String s = e.getMessage();
                s = s.replaceAll("[^@]*@@@|###[^@]*", "");
                exceptionThrown = new Exception(s);
            }
            else
            {
                exceptionThrown = new Exception(e.getMessage());
            }

        }

        return null;
    }

    @Override
    protected void onPreExecute()
    {
        try
        {
            progressDialog = ProgressDialog.show(myAct, "Please wait", message + "..", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void result)
    {
        try
        {
            if (progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }

            if (exceptionThrown != null)
            {
                Toast.makeText(myAct, exceptionThrown.getMessage(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    if(post!=null)
                        post.run();
                }
                catch(Exception e)
                {
                    Toast.makeText(myAct, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
