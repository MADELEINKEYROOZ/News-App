package com.example.newsapp.ui.contact;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsapp.R;
import com.example.newsapp.databinding.FragmentContactBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;

    Button btnSend;
    EditText iptName, iptEmail, iptPhone, iptMessage;
    TextView responseTV;
    ProgressBar loadingPB;

    boolean isAllFieldsChecked = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnSend = root.findViewById(R.id.send);

        iptName = root.findViewById(R.id.name);
        iptEmail = root.findViewById(R.id.email);
        iptPhone = root.findViewById(R.id.phone);
        iptMessage = root.findViewById(R.id.message);
        responseTV = root.findViewById(R.id.idTVResponse);
        loadingPB = root.findViewById(R.id.idLoadingPB);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    sendData();
                }
            }
        });
        return root;
    }

    private boolean CheckAllFields() {
        if (iptName.length() == 0) {
            iptName.setError("This field is required");
            return false;
        }

        if (iptEmail.length() == 0) {
            iptEmail.setError("Email is required");
            return false;
        }else if (iptEmail.length() > 0){
            String emailToText = iptEmail.getText().toString();

            if (Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            } else {
                iptEmail.setError("Enter valid Email address !");
                return false;
            }
        }

        if (iptPhone.length() == 0) {
            iptPhone.setError("Phone is required");
            return false;
        } else if (iptPhone.length() < 8) {
            iptPhone.setError("Phone must be minimum 8 characters");
            return false;
        }

        if (iptMessage.length() == 0) {
            iptMessage.setError("Message field is required");
            return false;
        }

        return true;
    }

    public void sendData(){
        postData(iptName.getText().toString(), iptEmail.getText().toString(), iptPhone.getText().toString(), iptMessage.getText().toString());
    }

    private void postData(String name, String email, String phone, String message) {
        loadingPB.setVisibility(View.VISIBLE);

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.androidExercise.com/contactus.aspx/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        DataModal modal = new DataModal(name, email, phone, message);

        // calling a method to create a post and passing our modal class.
        Call<DataModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                // this method is called when we get response from our api.
                Toast.makeText(getActivity(), "Data added to API", Toast.LENGTH_SHORT).show();

                loadingPB.setVisibility(View.GONE);

                // on below line we are setting empty text
                // to our both edit text.
                iptName.setText("");
                iptEmail.setText("");
                iptPhone.setText("");
                iptMessage.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Email : " + responseFromAPI.getEmail()+ "\n" + "Phone : " + responseFromAPI.getPhone() + "\n" + "Message : " + responseFromAPI.getMessage();

                // below line we are setting our
                // string to our text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}