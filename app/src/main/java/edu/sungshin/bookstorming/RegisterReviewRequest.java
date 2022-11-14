package edu.sungshin.bookstorming;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterReviewRequest extends StringRequest {

    final static private String URL = "http://qkrghksals00.dothome.co.kr/Review.php";
    private Map<String,String> map;

    public RegisterReviewRequest(String review, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("review",review);


    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
