package com.example.myapp.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.example.myapp.Model.MyResponse;
import com.example.myapp.R;
import com.example.myapp.Utils.Api;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class InputBukuJurnalAdminFragment extends Fragment {

    TextInputEditText textInputEditTextJudul,
            textInputEditTextDitulis_Oleh,
            textInputEditTextTahun_Terbit,
            textInputEditTextStok,
            editTextNama_Pdf,
            textInputEditTextEdisi,
            textInputEditTextNo_Panggil,
            textInputEditTextIsbn_Issn,
            textInputEditTextSubyek_Subjek,
            textInputEditTextKlasifikasi,
            textInputEditTextJudul_Seri,
            textInputEditTextGmd,
            textInputEditTextPenerbit,
            textInputEditTextBahasa,
            textInputEditTextTempat_Terbit,
            textInputEditTextAbstrak;



    AutoCompleteTextView textInputLayoutspin, textInputLayoutspin1, textInputEditTextStatus, textInputEditTextStatusbukujurnal;
    AppCompatButton buttonpdf, buttonimage, buttonCreate;
    ImageView imageViewcreate;
    Uri imageUri;
    Uri filepath;
    private AutoCompleteTextView dropdown, dropdown1;
    private int REQ_PDF = 21;

    public InputBukuJurnalAdminFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_input_buku_jurnal_admin, container, false);

        textInputEditTextJudul = (TextInputEditText) view.findViewById(R.id.judul_create);
        textInputEditTextDitulis_Oleh = (TextInputEditText) view.findViewById(R.id.ditulis_oleh_create);
        textInputEditTextTahun_Terbit = (TextInputEditText) view.findViewById(R.id.tahun_terbit_create);
        textInputEditTextStok = (TextInputEditText) view.findViewById(R.id.stok_buku_create);
        textInputEditTextStatus = (AutoCompleteTextView) view.findViewById(R.id.status_create);
        textInputEditTextStatusbukujurnal = (AutoCompleteTextView) view.findViewById(R.id.status_create_buku_jurnal);
        textInputEditTextEdisi = (TextInputEditText) view.findViewById(R.id.edisi_create);
        textInputEditTextNo_Panggil = (TextInputEditText) view.findViewById(R.id.no_panggil_create);
        textInputEditTextIsbn_Issn = (TextInputEditText) view.findViewById(R.id.isbn_issn_create);
        textInputEditTextSubyek_Subjek = (TextInputEditText) view.findViewById(R.id.subyek_subjek_create);
        textInputEditTextKlasifikasi = (TextInputEditText) view.findViewById(R.id.klasifikasi_create);
        textInputEditTextJudul_Seri = (TextInputEditText) view.findViewById(R.id.judul_seri_create);
        textInputEditTextGmd = (TextInputEditText) view.findViewById(R.id.gmd_create);
        textInputEditTextBahasa = (TextInputEditText) view.findViewById(R.id.bahasa_create);
        textInputEditTextPenerbit = (TextInputEditText) view.findViewById(R.id.penerbit_create);
        textInputEditTextTempat_Terbit = (TextInputEditText) view.findViewById(R.id.tempat_terbit_create);
        textInputEditTextAbstrak = (TextInputEditText) view.findViewById(R.id.abstrak_create);

        editTextNama_Pdf = (TextInputEditText) view.findViewById(R.id.nama_pdf);
        buttonCreate = (AppCompatButton) view.findViewById(R.id.button_tambah_data_admin);
        buttonimage = (AppCompatButton) view.findViewById(R.id.button_image_create_admin);
        buttonpdf = (AppCompatButton) view.findViewById(R.id.button_pdf_create_admin);
        imageViewcreate = (ImageView) view.findViewById(R.id.image_create);

        textInputLayoutspin = view.findViewById(R.id.status_create);
        textInputLayoutspin1 = view.findViewById(R.id.status_create_buku_jurnal);

        dropdown = view.findViewById(R.id.status_create);
        dropdown1 = view.findViewById(R.id.status_create_buku_jurnal);

        //pilih image
        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, REQ_PDF);
            }
        });

        //Buttom create
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUri != null) {
                    if (filepath != null) {

                        upload(imageUri, filepath,
                                textInputEditTextJudul.getText().toString(),
                                textInputEditTextDitulis_Oleh.getText().toString(),
                                textInputEditTextTahun_Terbit.getText().toString(),
                                textInputEditTextStatusbukujurnal.getText().toString(),
                                textInputEditTextStatus.getText().toString(),
                                textInputEditTextStok.getText().toString(),
                                textInputEditTextEdisi.getText().toString(),
                                textInputEditTextNo_Panggil.getText().toString(),
                                textInputEditTextIsbn_Issn.getText().toString(),
                                textInputEditTextSubyek_Subjek.getText().toString(),
                                textInputEditTextKlasifikasi.getText().toString(),
                                textInputEditTextJudul_Seri.getText().toString(),
                                textInputEditTextGmd.getText().toString(),
                                textInputEditTextBahasa.getText().toString(),
                                textInputEditTextPenerbit.getText().toString(),
                                textInputEditTextTempat_Terbit.getText().toString(),
                                textInputEditTextAbstrak.getText().toString()
                        );

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Masukkan Gambar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //SPINNER STATUS
        String[] items1 = new String[]{
                "buku",
                "jurnal",
        };

        //SPINNER STATUS_BUKU/JURNAL
        String[] items2 = new String[]{
                "tersedia",
        };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                getContext(), R.layout.support_simple_spinner_dropdown_item,
                items1
        );
        dropdown.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                getContext(), R.layout.support_simple_spinner_dropdown_item,
                items2
        );
        dropdown1.setAdapter(adapter2);

        //ijin akses image
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
        }
        return view;
    }

    private void upload(Uri fileUri,
                        Uri filepath,
                        String judul,
                        String ditulis_oleh,
                        String tahun_terbit,
                        String status_buku_jurnal,
                        String status,
                        String Stok,
                        String edisi,
                        String no_panggil,
                        String isbn_issn,
                        String subjek,
                        String klasifikasi,
                        String judul_seri,
                        String gmd,
                        String bahasa,
                        String penerbit,
                        String tempat_terbit,
                        String abstrak

    ) {

        File file = new File(getRealPathFromURI(fileUri));

        Log.e("IMAGE", getRealPathFromURI(fileUri));

        Log.e("PDF",getRealPathFromURI(filepath));

        File filepdf = new File(getRealPathFromURI(filepath));

//        Log.e("PDF",getRealPathFromURI(filepath));


        Toast.makeText(requireActivity().getApplicationContext(), "" + file.length(), Toast.LENGTH_SHORT).show();
        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);

        Toast.makeText(requireActivity().getApplicationContext(), "" + filepdf.length(), Toast.LENGTH_SHORT).show();
        RequestBody requestFilePDF = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), filepdf);

        RequestBody requestJudul = RequestBody.create(MediaType.parse("text/plain"), judul);
        RequestBody requestDitulis_Oleh = RequestBody.create(MediaType.parse("text/plain"), ditulis_oleh);
        RequestBody requestTahun_terbit = RequestBody.create(MediaType.parse("text/plain"), tahun_terbit);
        RequestBody requestStatus_buku_jurnal = RequestBody.create(MediaType.parse("text/plain"), status_buku_jurnal);
        RequestBody requestStok = RequestBody.create(MediaType.parse("text/plain"), Stok);
        RequestBody requestStatus = RequestBody.create(MediaType.parse("text/plain"), status);
        RequestBody requestEdisi = RequestBody.create(MediaType.parse("text/plain"), edisi);
        RequestBody requestNo_panggil = RequestBody.create(MediaType.parse("text/plain"), no_panggil);
        RequestBody requestIbn_Issn = RequestBody.create(MediaType.parse("text/plain"), isbn_issn);
        RequestBody requestSubyek_Subjek = RequestBody.create(MediaType.parse("text/plain"), subjek);
        RequestBody requestKlasifikasi = RequestBody.create(MediaType.parse("text/plain"), klasifikasi);
        RequestBody requestJudul_Seri = RequestBody.create(MediaType.parse("text/plain"), judul_seri);
        RequestBody requestGmd = RequestBody.create(MediaType.parse("text/plain"),gmd );
        RequestBody requestBahasa = RequestBody.create(MediaType.parse("text/plain"),bahasa );
        RequestBody requesPenerbit = RequestBody.create(MediaType.parse("text/plain"),penerbit );
        RequestBody requestTempat_Terbit = RequestBody.create(MediaType.parse("text/plain"),tempat_terbit );
        RequestBody requestAbstrak = RequestBody.create(MediaType.parse("text/plain"),abstrak );


        if (Stok.isEmpty()) {
            textInputEditTextStok.setError("Stok tidak boleh kosong");
            textInputEditTextStok.requestFocus();
            return;
        }

        if (judul.isEmpty()) {
            textInputEditTextJudul.setError("Judul tidak boleh kosong");
            textInputEditTextJudul.requestFocus();
            return;
        }
        if (ditulis_oleh.isEmpty()) {
            textInputEditTextDitulis_Oleh.setError("Penulis tidak boleh kosong");
            textInputEditTextDitulis_Oleh.requestFocus();
            return;
        }
        if (tahun_terbit.isEmpty()) {
            textInputEditTextTahun_Terbit.setError("Tahun terbit tidak boleh kosong");
            textInputEditTextTahun_Terbit.requestFocus();
            return;
        }
        if (status_buku_jurnal.isEmpty()) {
            textInputEditTextStatusbukujurnal.setError("Pilih status buku/jurnal");
            textInputEditTextStatusbukujurnal.requestFocus();
            return;
        }
        if (status.isEmpty()) {
            textInputEditTextStatus.setError("Pilih status");
            textInputEditTextStatus.requestFocus();
            return;
        }
        if (edisi.isEmpty()) {
            textInputEditTextEdisi.setError("Edisi tidak boleh kosong");
            textInputEditTextEdisi.requestFocus();
            return;
        }
        if (no_panggil.isEmpty()) {
            textInputEditTextNo_Panggil.setError("No Panggil tidak boleh kosong");
            textInputEditTextNo_Panggil.requestFocus();
            return;
        }
        if (isbn_issn.isEmpty()) {
            textInputEditTextIsbn_Issn.setError("ISBN/ISSN tidak boleh kosong");
            textInputEditTextIsbn_Issn.requestFocus();
            return;
        }
        if (subjek.isEmpty()) {
            textInputEditTextSubyek_Subjek.setError("Subyek/Subjek tidak boleh kosong");
            textInputEditTextSubyek_Subjek.requestFocus();
            return;
        }
        if (klasifikasi.isEmpty()) {
            textInputEditTextKlasifikasi.setError("Klasifikasi tidak boleh kosong");
            textInputEditTextKlasifikasi.requestFocus();
            return;
        }
        if (judul_seri.isEmpty()) {
            textInputEditTextJudul_Seri.setError("Judul Seri tidak boleh kosong");
            textInputEditTextJudul_Seri.requestFocus();
            return;
        }
        if (gmd.isEmpty()) {
            textInputEditTextGmd.setError("GMD tidak boleh kosong");
            textInputEditTextGmd.requestFocus();
            return;
        }
        if (bahasa.isEmpty()) {
            textInputEditTextBahasa.setError("Bahasa tidak boleh kosong");
            textInputEditTextBahasa.requestFocus();
            return;
        }
        if (tempat_terbit.isEmpty()) {
            textInputEditTextTempat_Terbit.setError("Tempat Terbit tidak boleh kosong");
            textInputEditTextTempat_Terbit.requestFocus();
            return;
        }
        if (abstrak.isEmpty()) {
            textInputEditTextAbstrak.setError("Abstrak tidak boleh kosong");
            textInputEditTextAbstrak.requestFocus();
            return;
        }


        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_BASE_URL + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        Log.e("STOK",Stok);
        Call<MyResponse> call = api.upload(requestFile,
                requestFilePDF,
                requestJudul,
                requestDitulis_Oleh,
                requestTahun_terbit,
                requestStatus_buku_jurnal,
                requestStatus,
                requestStok,
                requestEdisi,
                requestNo_panggil,
                requestIbn_Issn,
                requestSubyek_Subjek,
                requestKlasifikasi,
                requestJudul_Seri,
                requestGmd,
                requestBahasa,
                requesPenerbit,
                requestTempat_Terbit,
                requestAbstrak


        );
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.body().isError()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Sukses Upload", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity().getApplicationContext(), "Gagal Upload", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }

        //pdf
        if (requestCode == REQ_PDF && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {
                InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);

//                filepath = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);

                filepath = data.getData();

                editTextNama_Pdf.setText("Document Selected");
                buttonpdf.setText("Change Document");

                Toast.makeText(getActivity().getApplicationContext(), "Document Selected", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int columIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(columIndex);
        cursor.close();
        return result;
    }

}
