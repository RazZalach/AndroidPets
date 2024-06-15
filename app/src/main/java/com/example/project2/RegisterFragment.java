package com.example.project2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class RegisterFragment extends Fragment {

    EditText etName, etBody, etPicId;
    Button btnSave, btnSelectImage, selectImageButton;
    DbDiary dbDiary;
    private static final int REQUEST_IMAGE_GET = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        dbDiary = new DbDiary(requireContext());

        etName = view.findViewById(R.id.etName);
        etBody = view.findViewById(R.id.etBody);
        etPicId = view.findViewById(R.id.etPicId);
        btnSave = view.findViewById(R.id.btnSave);
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        selectImageButton = view.findViewById(R.id.selectImageButton);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectionDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedImagePath = etPicId.getText().toString();
                String name = etName.getText().toString();
                String body = etBody.getText().toString();
                dbDiary.addDiaryPage(new DiaryPage(name, body, selectedImagePath));
                Toast.makeText(getContext(), "Diary page saved successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), data.getData());
                    String filename = saveToInternalStorage(bitmap);
                    Toast.makeText(requireContext(), "Image saved as " + filename, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmap) throws IOException {
        File directory = requireContext().getDir("images", getContext().MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filename = "image_" + new Date().getTime() + ".png";
        File imageFile = new File(directory, filename);
        FileOutputStream fos = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
        return imageFile.getAbsolutePath();
    }

    private void showImageSelectionDialog() {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.dialog_image_selection, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        LinearLayout imageOptionsContainer = dialogView.findViewById(R.id.imageOptionsContainer);

        // Get the internal storage directory
        File directory = requireContext().getDir("images", getContext().MODE_PRIVATE);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                try {
                    ImageView imageView = new ImageView(requireContext());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), Uri.fromFile(file));
                    imageView.setImageBitmap(bitmap);
                    imageView.setPadding(8, 8, 8, 8);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
                    layoutParams.setMargins(8, 8, 8, 8); // Set margins if needed
                    imageView.setLayoutParams(layoutParams);
                    imageView.setClickable(true);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etPicId.setText(file.getAbsolutePath());
                            dialog.dismiss();
                        }
                    });
                    imageOptionsContainer.addView(imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        dialog.show();
    }
}
