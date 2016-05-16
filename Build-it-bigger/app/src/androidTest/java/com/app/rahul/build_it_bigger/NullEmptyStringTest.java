package com.app.rahul.build_it_bigger;

import android.test.AndroidTestCase;
import android.util.Log;

public class NullEmptyStringTest extends AndroidTestCase {

    private static final String LOG_TAG = NullEmptyStringTest.class.getSimpleName();

    public void test() {

        // Testing that Async task successfully retrieves a non-empty string
        // You can test this from androidTest -> Run 'All Tests'
        Log.v("NullEmptyStringTest", "Running NullEmptyStringTest test");
        String result = null;
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(getContext(), null);
        endpointsAsyncTask.execute();
        try {
            result = endpointsAsyncTask.get();
            Log.d(LOG_TAG, "Retrieved a non-empty string successfully: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(result);
    }

}
