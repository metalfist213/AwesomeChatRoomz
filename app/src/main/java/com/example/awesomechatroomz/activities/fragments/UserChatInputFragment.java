package com.example.awesomechatroomz.activities.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.awesomechatroomz.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserChatInputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserChatInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserChatInputFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_LOAD_IMAGE = 0;
    private static final int RESULT_TAKE_PICTURE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button sendButton;
    private ImageButton choosePictureImageButton;
    private ImageButton cameraImageButton;
    private EditText chatEditText;

    private OnFragmentInteractionListener mListener;

    public UserChatInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserChatInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserChatInputFragment newInstance(String param1, String param2) {
        UserChatInputFragment fragment = new UserChatInputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflated = inflater.inflate(R.layout.fragment_user_chat_input, container, false);

        sendButton = inflated.findViewById(R.id.send_button);
        choosePictureImageButton = inflated.findViewById(R.id.choose_picture_button);
        cameraImageButton = inflated.findViewById(R.id.take_picture_button);
        chatEditText = inflated.findViewById(R.id.chatEditText);
        setListeners();

        return inflated;
    }

    private void setListeners() {
        setOnSend();
        setOnUpload();
        setOnTakePicture();
    }

    private void setOnTakePicture() {
        cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCameraPermission()) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, RESULT_TAKE_PICTURE);
                        mListener.onCameraUploadRequest();
                    }
                }
            }
        });
    }

    private boolean getCameraPermission() {
        if(!hasCameraPermission()) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, getActivity().getPackageManager().PERMISSION_GRANTED);

            return hasCameraPermission();
        } else {
            return true;
        }
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == getActivity().getPackageManager().PERMISSION_GRANTED;
    }

    private void setOnUpload() {
        choosePictureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                mListener.onCameraUploadRequest();
            }
        });
    }

    private void setOnSend() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
            }
        });
        chatEditText.setImeActionLabel("Send!", KeyEvent.KEYCODE_ENTER);
        chatEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND) {
                    sendButton.performClick();
                }

                return false;
            }
        });
    }

    private void sendText() {
        String text = chatEditText.getText().toString();

        if (text != null && text != "") { //No reason to send empty text!
            mListener.onTextSend(text);
            chatEditText.getText().clear();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE) {
            System.out.println("LOAD IMAGE");
        } else {
            System.out.println("LOAD CAMERA");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onTextSend(String text);

        void onImageUploadRequest();

        void onCameraUploadRequest();
    }
}
