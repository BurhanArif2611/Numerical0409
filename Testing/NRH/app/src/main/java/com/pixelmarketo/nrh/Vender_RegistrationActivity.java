package com.pixelmarketo.nrh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.pixelmarketo.nrh.adapter.Vender_services_adapter;
import com.pixelmarketo.nrh.database.UserProfileHelper;
import com.pixelmarketo.nrh.database.UserProfileModel;
import com.pixelmarketo.nrh.models.Service_Models.Example;
import com.pixelmarketo.nrh.utility.AppConfig;
import com.pixelmarketo.nrh.utility.ErrorMessage;
import com.pixelmarketo.nrh.utility.LoadInterface;
import com.pixelmarketo.nrh.utility.NetworkUtil;
import com.pixelmarketo.nrh.utility.SavedData;
import com.pixelmarketo.nrh.utility.UserAccount;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class Vender_RegistrationActivity extends AppCompatActivity implements TextWatcher, PaymentResultListener {

    @BindView(R.id.user_name_et)
    EditText userNameEt;
    @BindView(R.id.email_textinputlayout)
    TextInputLayout emailTextinputlayout;
    @BindView(R.id.mobile_number_et)
    EditText mobileNumberEt;
    @BindView(R.id.my_service_Rv)
    RecyclerView myServiceRv;
    @BindView(R.id.state_spinner)
    AutoCompleteTextView stateSpinner;
    @BindView(R.id.district_spinner)
    AutoCompleteTextView districtSpinner;
    @BindView(R.id.tehsil_etv)
    EditText tehsilEtv;

    @BindView(R.id.address_etv)
    EditText addressEtv;
    @BindView(R.id.pincode_etv)
    EditText pincodeEtv;
    @BindView(R.id.password_etv)
    EditText passwordEtv;
    @BindView(R.id.confirm_password_etv)
    EditText confirmPasswordEtv;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.upload_documentlink_tv)
    TextView uploadDocumentlinkTv;
    @BindView(R.id.upload_tv)
    TextView uploadTv;
    @BindView(R.id.city_spinner)
    AutoCompleteTextView citySpinner;
    @BindView(R.id.terms_condition_checkbox)
    CheckBox termsConditionCheckbox;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    EditText editTextone, editTexttwo, editTextthree, editTextfour, editTextfifth, editTextsixth, mobilenumber_et;
    Dialog dialog;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    private String selectedImagePath = "";
    Uri mImageCaptureUri;
    File finalFile;
    ArrayList<String> service_idsList = new ArrayList<>();
    ArrayList<String> stateArrayList = new ArrayList<>();
    ArrayList<String> districtArrayList = new ArrayList<>();
    private String Selected_State = "", Selected_district = "", Select_City = "";
    String State = "{  \n" + "   \"states\":[  \n" + "      {  \n" + "         \"state\":\"Andhra Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Anantapur\",\n" + "            \"Chittoor\",\n" + "            \"East Godavari\",\n" + "            \"Guntur\",\n" + "            \"Krishna\",\n" + "            \"Kurnool\",\n" + "            \"Nellore\",\n" + "            \"Prakasam\",\n" + "            \"Srikakulam\",\n" + "            \"Visakhapatnam\",\n" + "            \"Vizianagaram\",\n" + "            \"West Godavari\",\n" + "            \"YSR Kadapa\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Arunachal Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Tawang\",\n" + "            \"West Kameng\",\n" + "            \"East Kameng\",\n" + "            \"Papum Pare\",\n" + "            \"Kurung Kumey\",\n" + "            \"Kra Daadi\",\n" + "            \"Lower Subansiri\",\n" + "            \"Upper Subansiri\",\n" + "            \"West Siang\",\n" + "            \"East Siang\",\n" + "            \"Siang\",\n" + "            \"Upper Siang\",\n" + "            \"Lower Siang\",\n" + "            \"Lower Dibang Valley\",\n" + "            \"Dibang Valley\",\n" + "            \"Anjaw\",\n" + "            \"Lohit\",\n" + "            \"Namsai\",\n" + "            \"Changlang\",\n" + "            \"Tirap\",\n" + "            \"Longding\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Assam\",\n" + "         \"districts\":[  \n" + "            \"Baksa\",\n" + "            \"Barpeta\",\n" + "            \"Biswanath\",\n" + "            \"Bongaigaon\",\n" + "            \"Cachar\",\n" + "            \"Charaideo\",\n" + "            \"Chirang\",\n" + "            \"Darrang\",\n" + "            \"Dhemaji\",\n" + "            \"Dhubri\",\n" + "            \"Dibrugarh\",\n" + "            \"Goalpara\",\n" + "            \"Golaghat\",\n" + "            \"Hailakandi\",\n" + "            \"Hojai\",\n" + "            \"Jorhat\",\n" + "            \"Kamrup Metropolitan\",\n" + "            \"Kamrup\",\n" + "            \"Karbi Anglong\",\n" + "            \"Karimganj\",\n" + "            \"Kokrajhar\",\n" + "            \"Lakhimpur\",\n" + "            \"Majuli\",\n" + "            \"Morigaon\",\n" + "            \"Nagaon\",\n" + "            \"Nalbari\",\n" + "            \"Dima Hasao\",\n" + "            \"Sivasagar\",\n" + "            \"Sonitpur\",\n" + "            \"South Salmara-Mankachar\",\n" + "            \"Tinsukia\",\n" + "            \"Udalguri\",\n" + "            \"West Karbi Anglong\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Bihar\",\n" + "         \"districts\":[  \n" + "            \"Araria\",\n" + "            \"Arwal\",\n" + "            \"Aurangabad\",\n" + "            \"Banka\",\n" + "            \"Begusarai\",\n" + "            \"Bhagalpur\",\n" + "            \"Bhojpur\",\n" + "            \"Buxar\",\n" + "            \"Darbhanga\",\n" + "            \"East Champaran (Motihari)\",\n" + "            \"Gaya\",\n" + "            \"Gopalganj\",\n" + "            \"Jamui\",\n" + "            \"Jehanabad\",\n" + "            \"Kaimur (Bhabua)\",\n" + "            \"Katihar\",\n" + "            \"Khagaria\",\n" + "            \"Kishanganj\",\n" + "            \"Lakhisarai\",\n" + "            \"Madhepura\",\n" + "            \"Madhubani\",\n" + "            \"Munger (Monghyr)\",\n" + "            \"Muzaffarpur\",\n" + "            \"Nalanda\",\n" + "            \"Nawada\",\n" + "            \"Patna\",\n" + "            \"Purnia (Purnea)\",\n" + "            \"Rohtas\",\n" + "            \"Saharsa\",\n" + "            \"Samastipur\",\n" + "            \"Saran\",\n" + "            \"Sheikhpura\",\n" + "            \"Sheohar\",\n" + "            \"Sitamarhi\",\n" + "            \"Siwan\",\n" + "            \"Supaul\",\n" + "            \"Vaishali\",\n" + "            \"West Champaran\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Chandigarh (UT)\",\n" + "         \"districts\":[  \n" + "            \"Chandigarh\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Chhattisgarh\",\n" + "         \"districts\":[  \n" + "            \"Balod\",\n" + "            \"Baloda Bazar\",\n" + "            \"Balrampur\",\n" + "            \"Bastar\",\n" + "            \"Bemetara\",\n" + "            \"Bijapur\",\n" + "            \"Bilaspur\",\n" + "            \"Dantewada (South Bastar)\",\n" + "            \"Dhamtari\",\n" + "            \"Durg\",\n" + "            \"Gariyaband\",\n" + "            \"Janjgir-Champa\",\n" + "            \"Jashpur\",\n" + "            \"Kabirdham (Kawardha)\",\n" + "            \"Kanker (North Bastar)\",\n" + "            \"Kondagaon\",\n" + "            \"Korba\",\n" + "            \"Korea (Koriya)\",\n" + "            \"Mahasamund\",\n" + "            \"Mungeli\",\n" + "            \"Narayanpur\",\n" + "            \"Raigarh\",\n" + "            \"Raipur\",\n" + "            \"Rajnandgaon\",\n" + "            \"Sukma\",\n" + "            \"Surajpur  \",\n" + "            \"Surguja\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Dadra and Nagar Haveli (UT)\",\n" + "         \"districts\":[  \n" + "            \"Dadra & Nagar Haveli\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Daman and Diu (UT)\",\n" + "         \"districts\":[  \n" + "            \"Daman\",\n" + "            \"Diu\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Delhi (NCT)\",\n" + "         \"districts\":[  \n" + "            \"Central Delhi\",\n" + "            \"East Delhi\",\n" + "            \"New Delhi\",\n" + "            \"North Delhi\",\n" + "            \"North East  Delhi\",\n" + "            \"North West  Delhi\",\n" + "            \"Shahdara\",\n" + "            \"South Delhi\",\n" + "            \"South East Delhi\",\n" + "            \"South West  Delhi\",\n" + "            \"West Delhi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Goa\",\n" + "         \"districts\":[  \n" + "            \"North Goa\",\n" + "            \"South Goa\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Gujarat\",\n" + "         \"districts\":[  \n" + "            \"Ahmedabad\",\n" + "            \"Amreli\",\n" + "            \"Anand\",\n" + "            \"Aravalli\",\n" + "            \"Banaskantha (Palanpur)\",\n" + "            \"Bharuch\",\n" + "            \"Bhavnagar\",\n" + "            \"Botad\",\n" + "            \"Chhota Udepur\",\n" + "            \"Dahod\",\n" + "            \"Dangs (Ahwa)\",\n" + "            \"Devbhoomi Dwarka\",\n" + "            \"Gandhinagar\",\n" + "            \"Gir Somnath\",\n" + "            \"Jamnagar\",\n" + "            \"Junagadh\",\n" + "            \"Kachchh\",\n" + "            \"Kheda (Nadiad)\",\n" + "            \"Mahisagar\",\n" + "            \"Mehsana\",\n" + "            \"Morbi\",\n" + "            \"Narmada (Rajpipla)\",\n" + "            \"Navsari\",\n" + "            \"Panchmahal (Godhra)\",\n" + "            \"Patan\",\n" + "            \"Porbandar\",\n" + "            \"Rajkot\",\n" + "            \"Sabarkantha (Himmatnagar)\",\n" + "            \"Surat\",\n" + "            \"Surendranagar\",\n" + "            \"Tapi (Vyara)\",\n" + "            \"Vadodara\",\n" + "            \"Valsad\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Haryana\",\n" + "         \"districts\":[  \n" + "            \"Ambala\",\n" + "            \"Bhiwani\",\n" + "            \"Charkhi Dadri\",\n" + "            \"Faridabad\",\n" + "            \"Fatehabad\",\n" + "            \"Gurgaon\",\n" + "            \"Hisar\",\n" + "            \"Jhajjar\",\n" + "            \"Jind\",\n" + "            \"Kaithal\",\n" + "            \"Karnal\",\n" + "            \"Kurukshetra\",\n" + "            \"Mahendragarh\",\n" + "            \"Mewat\",\n" + "            \"Palwal\",\n" + "            \"Panchkula\",\n" + "            \"Panipat\",\n" + "            \"Rewari\",\n" + "            \"Rohtak\",\n" + "            \"Sirsa\",\n" + "            \"Sonipat\",\n" + "            \"Yamunanagar\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Himachal Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Bilaspur\",\n" + "            \"Chamba\",\n" + "            \"Hamirpur\",\n" + "            \"Kangra\",\n" + "            \"Kinnaur\",\n" + "            \"Kullu\",\n" + "            \"Lahaul &amp; Spiti\",\n" + "            \"Mandi\",\n" + "            \"Shimla\",\n" + "            \"Sirmaur (Sirmour)\",\n" + "            \"Solan\",\n" + "            \"Una\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Jammu and Kashmir\",\n" + "         \"districts\":[  \n" + "            \"Anantnag\",\n" + "            \"Bandipore\",\n" + "            \"Baramulla\",\n" + "            \"Budgam\",\n" + "            \"Doda\",\n" + "            \"Ganderbal\",\n" + "            \"Jammu\",\n" + "            \"Kargil\",\n" + "            \"Kathua\",\n" + "            \"Kishtwar\",\n" + "            \"Kulgam\",\n" + "            \"Kupwara\",\n" + "            \"Leh\",\n" + "            \"Poonch\",\n" + "            \"Pulwama\",\n" + "            \"Rajouri\",\n" + "            \"Ramban\",\n" + "            \"Reasi\",\n" + "            \"Samba\",\n" + "            \"Shopian\",\n" + "            \"Srinagar\",\n" + "            \"Udhampur\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Jharkhand\",\n" + "         \"districts\":[  \n" + "            \"Bokaro\",\n" + "            \"Chatra\",\n" + "            \"Deoghar\",\n" + "            \"Dhanbad\",\n" + "            \"Dumka\",\n" + "            \"East Singhbhum\",\n" + "            \"Garhwa\",\n" + "            \"Giridih\",\n" + "            \"Godda\",\n" + "            \"Gumla\",\n" + "            \"Hazaribag\",\n" + "            \"Jamtara\",\n" + "            \"Khunti\",\n" + "            \"Koderma\",\n" + "            \"Latehar\",\n" + "            \"Lohardaga\",\n" + "            \"Pakur\",\n" + "            \"Palamu\",\n" + "            \"Ramgarh\",\n" + "            \"Ranchi\",\n" + "            \"Sahibganj\",\n" + "            \"Seraikela-Kharsawan\",\n" + "            \"Simdega\",\n" + "            \"West Singhbhum\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Karnataka\",\n" + "         \"districts\":[  \n" + "            \"Bagalkot\",\n" + "            \"Ballari (Bellary)\",\n" + "            \"Belagavi (Belgaum)\",\n" + "            \"Bengaluru (Bangalore) Rural\",\n" + "            \"Bengaluru (Bangalore) Urban\",\n" + "            \"Bidar\",\n" + "            \"Chamarajanagar\",\n" + "            \"Chikballapur\",\n" + "            \"Chikkamagaluru (Chikmagalur)\",\n" + "            \"Chitradurga\",\n" + "            \"Dakshina Kannada\",\n" + "            \"Davangere\",\n" + "            \"Dharwad\",\n" + "            \"Gadag\",\n" + "            \"Hassan\",\n" + "            \"Haveri\",\n" + "            \"Kalaburagi (Gulbarga)\",\n" + "            \"Kodagu\",\n" + "            \"Kolar\",\n" + "            \"Koppal\",\n" + "            \"Mandya\",\n" + "            \"Mysuru (Mysore)\",\n" + "            \"Raichur\",\n" + "            \"Ramanagara\",\n" + "            \"Shivamogga (Shimoga)\",\n" + "            \"Tumakuru (Tumkur)\",\n" + "            \"Udupi\",\n" + "            \"Uttara Kannada (Karwar)\",\n" + "            \"Vijayapura (Bijapur)\",\n" + "            \"Yadgir\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Kerala\",\n" + "         \"districts\":[  \n" + "            \"Alappuzha\",\n" + "            \"Ernakulam\",\n" + "            \"Idukki\",\n" + "            \"Kannur\",\n" + "            \"Kasaragod\",\n" + "            \"Kollam\",\n" + "            \"Kottayam\",\n" + "            \"Kozhikode\",\n" + "            \"Malappuram\",\n" + "            \"Palakkad\",\n" + "            \"Pathanamthitta\",\n" + "            \"Thiruvananthapuram\",\n" + "            \"Thrissur\",\n" + "            \"Wayanad\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Lakshadweep (UT)\",\n" + "         \"districts\":[  \n" + "            \"Agatti\",\n" + "            \"Amini\",\n" + "            \"Androth\",\n" + "            \"Bithra\",\n" + "            \"Chethlath\",\n" + "            \"Kavaratti\",\n" + "            \"Kadmath\",\n" + "            \"Kalpeni\",\n" + "            \"Kilthan\",\n" + "            \"Minicoy\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Madhya Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Agar Malwa\",\n" + "            \"Alirajpur\",\n" + "            \"Anuppur\",\n" + "            \"Ashoknagar\",\n" + "            \"Balaghat\",\n" + "            \"Barwani\",\n" + "            \"Betul\",\n" + "            \"Bhind\",\n" + "            \"Bhopal\",\n" + "            \"Burhanpur\",\n" + "            \"Chhatarpur\",\n" + "            \"Chhindwara\",\n" + "            \"Damoh\",\n" + "            \"Datia\",\n" + "            \"Dewas\",\n" + "            \"Dhar\",\n" + "            \"Dindori\",\n" + "            \"Guna\",\n" + "            \"Gwalior\",\n" + "            \"Harda\",\n" + "            \"Hoshangabad\",\n" + "            \"Indore\",\n" + "            \"Jabalpur\",\n" + "            \"Jhabua\",\n" + "            \"Katni\",\n" + "            \"Khandwa\",\n" + "            \"Khargone\",\n" + "            \"Mandla\",\n" + "            \"Mandsaur\",\n" + "            \"Morena\",\n" + "            \"Narsinghpur\",\n" + "            \"Neemuch\",\n" + "            \"Panna\",\n" + "            \"Raisen\",\n" + "            \"Rajgarh\",\n" + "            \"Ratlam\",\n" + "            \"Rewa\",\n" + "            \"Sagar\",\n" + "            \"Satna\",\n" + "            \"Sehore\",\n" + "            \"Seoni\",\n" + "            \"Shahdol\",\n" + "            \"Shajapur\",\n" + "            \"Sheopur\",\n" + "            \"Shivpuri\",\n" + "            \"Sidhi\",\n" + "            \"Singrauli\",\n" + "            \"Tikamgarh\",\n" + "            \"Ujjain\",\n" + "            \"Umaria\",\n" + "            \"Vidisha\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Maharashtra\",\n" + "         \"districts\":[  \n" + "            \"Ahmednagar\",\n" + "            \"Akola\",\n" + "            \"Amravati\",\n" + "            \"Aurangabad\",\n" + "            \"Beed\",\n" + "            \"Bhandara\",\n" + "            \"Buldhana\",\n" + "            \"Chandrapur\",\n" + "            \"Dhule\",\n" + "            \"Gadchiroli\",\n" + "            \"Gondia\",\n" + "            \"Hingoli\",\n" + "            \"Jalgaon\",\n" + "            \"Jalna\",\n" + "            \"Kolhapur\",\n" + "            \"Latur\",\n" + "            \"Mumbai City\",\n" + "            \"Mumbai Suburban\",\n" + "            \"Nagpur\",\n" + "            \"Nanded\",\n" + "            \"Nandurbar\",\n" + "            \"Nashik\",\n" + "            \"Osmanabad\",\n" + "            \"Palghar\",\n" + "            \"Parbhani\",\n" + "            \"Pune\",\n" + "            \"Raigad\",\n" + "            \"Ratnagiri\",\n" + "            \"Sangli\",\n" + "            \"Satara\",\n" + "            \"Sindhudurg\",\n" + "            \"Solapur\",\n" + "            \"Thane\",\n" + "            \"Wardha\",\n" + "            \"Washim\",\n" + "            \"Yavatmal\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Manipur\",\n" + "         \"districts\":[  \n" + "            \"Bishnupur\",\n" + "            \"Chandel\",\n" + "            \"Churachandpur\",\n" + "            \"Imphal East\",\n" + "            \"Imphal West\",\n" + "            \"Jiribam\",\n" + "            \"Kakching\",\n" + "            \"Kamjong\",\n" + "            \"Kangpokpi\",\n" + "            \"Noney\",\n" + "            \"Pherzawl\",\n" + "            \"Senapati\",\n" + "            \"Tamenglong\",\n" + "            \"Tengnoupal\",\n" + "            \"Thoubal\",\n" + "            \"Ukhrul\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Meghalaya\",\n" + "         \"districts\":[  \n" + "            \"East Garo Hills\",\n" + "            \"East Jaintia Hills\",\n" + "            \"East Khasi Hills\",\n" + "            \"North Garo Hills\",\n" + "            \"Ri Bhoi\",\n" + "            \"South Garo Hills\",\n" + "            \"South West Garo Hills \",\n" + "            \"South West Khasi Hills\",\n" + "            \"West Garo Hills\",\n" + "            \"West Jaintia Hills\",\n" + "            \"West Khasi Hills\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Mizoram\",\n" + "         \"districts\":[  \n" + "            \"Aizawl\",\n" + "            \"Champhai\",\n" + "            \"Kolasib\",\n" + "            \"Lawngtlai\",\n" + "            \"Lunglei\",\n" + "            \"Mamit\",\n" + "            \"Saiha\",\n" + "            \"Serchhip\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Nagaland\",\n" + "         \"districts\":[  \n" + "            \"Dimapur\",\n" + "            \"Kiphire\",\n" + "            \"Kohima\",\n" + "            \"Longleng\",\n" + "            \"Mokokchung\",\n" + "            \"Mon\",\n" + "            \"Peren\",\n" + "            \"Phek\",\n" + "            \"Tuensang\",\n" + "            \"Wokha\",\n" + "            \"Zunheboto\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Odisha\",\n" + "         \"districts\":[  \n" + "            \"Angul\",\n" + "            \"Balangir\",\n" + "            \"Balasore\",\n" + "            \"Bargarh\",\n" + "            \"Bhadrak\",\n" + "            \"Boudh\",\n" + "            \"Cuttack\",\n" + "            \"Deogarh\",\n" + "            \"Dhenkanal\",\n" + "            \"Gajapati\",\n" + "            \"Ganjam\",\n" + "            \"Jagatsinghapur\",\n" + "            \"Jajpur\",\n" + "            \"Jharsuguda\",\n" + "            \"Kalahandi\",\n" + "            \"Kandhamal\",\n" + "            \"Kendrapara\",\n" + "            \"Kendujhar (Keonjhar)\",\n" + "            \"Khordha\",\n" + "            \"Koraput\",\n" + "            \"Malkangiri\",\n" + "            \"Mayurbhanj\",\n" + "            \"Nabarangpur\",\n" + "            \"Nayagarh\",\n" + "            \"Nuapada\",\n" + "            \"Puri\",\n" + "            \"Rayagada\",\n" + "            \"Sambalpur\",\n" + "            \"Sonepur\",\n" + "            \"Sundargarh\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Puducherry (UT)\",\n" + "         \"districts\":[  \n" + "            \"Karaikal\",\n" + "            \"Mahe\",\n" + "            \"Pondicherry\",\n" + "            \"Yanam\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Punjab\",\n" + "         \"districts\":[  \n" + "            \"Amritsar\",\n" + "            \"Barnala\",\n" + "            \"Bathinda\",\n" + "            \"Faridkot\",\n" + "            \"Fatehgarh Sahib\",\n" + "            \"Fazilka\",\n" + "            \"Ferozepur\",\n" + "            \"Gurdaspur\",\n" + "            \"Hoshiarpur\",\n" + "            \"Jalandhar\",\n" + "            \"Kapurthala\",\n" + "            \"Ludhiana\",\n" + "            \"Mansa\",\n" + "            \"Moga\",\n" + "            \"Muktsar\",\n" + "            \"Nawanshahr (Shahid Bhagat Singh Nagar)\",\n" + "            \"Pathankot\",\n" + "            \"Patiala\",\n" + "            \"Rupnagar\",\n" + "            \"Sahibzada Ajit Singh Nagar (Mohali)\",\n" + "            \"Sangrur\",\n" + "            \"Tarn Taran\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Rajasthan\",\n" + "         \"districts\":[  \n" + "            \"Ajmer\",\n" + "            \"Alwar\",\n" + "            \"Banswara\",\n" + "            \"Baran\",\n" + "            \"Barmer\",\n" + "            \"Bharatpur\",\n" + "            \"Bhilwara\",\n" + "            \"Bikaner\",\n" + "            \"Bundi\",\n" + "            \"Chittorgarh\",\n" + "            \"Churu\",\n" + "            \"Dausa\",\n" + "            \"Dholpur\",\n" + "            \"Dungarpur\",\n" + "            \"Hanumangarh\",\n" + "            \"Jaipur\",\n" + "            \"Jaisalmer\",\n" + "            \"Jalore\",\n" + "            \"Jhalawar\",\n" + "            \"Jhunjhunu\",\n" + "            \"Jodhpur\",\n" + "            \"Karauli\",\n" + "            \"Kota\",\n" + "            \"Nagaur\",\n" + "            \"Pali\",\n" + "            \"Pratapgarh\",\n" + "            \"Rajsamand\",\n" + "            \"Sawai Madhopur\",\n" + "            \"Sikar\",\n" + "            \"Sirohi\",\n" + "            \"Sri Ganganagar\",\n" + "            \"Tonk\",\n" + "            \"Udaipur\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Sikkim\",\n" + "         \"districts\":[  \n" + "            \"East Sikkim\",\n" + "            \"North Sikkim\",\n" + "            \"South Sikkim\",\n" + "            \"West Sikkim\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Tamil Nadu\",\n" + "         \"districts\":[  \n" + "            \"Ariyalur\",\n" + "            \"Chennai\",\n" + "            \"Coimbatore\",\n" + "            \"Cuddalore\",\n" + "            \"Dharmapuri\",\n" + "            \"Dindigul\",\n" + "            \"Erode\",\n" + "            \"Kanchipuram\",\n" + "            \"Kanyakumari\",\n" + "            \"Karur\",\n" + "            \"Krishnagiri\",\n" + "            \"Madurai\",\n" + "            \"Nagapattinam\",\n" + "            \"Namakkal\",\n" + "            \"Nilgiris\",\n" + "            \"Perambalur\",\n" + "            \"Pudukkottai\",\n" + "            \"Ramanathapuram\",\n" + "            \"Salem\",\n" + "            \"Sivaganga\",\n" + "            \"Thanjavur\",\n" + "            \"Theni\",\n" + "            \"Thoothukudi (Tuticorin)\",\n" + "            \"Tiruchirappalli\",\n" + "            \"Tirunelveli\",\n" + "            \"Tiruppur\",\n" + "            \"Tiruvallur\",\n" + "            \"Tiruvannamalai\",\n" + "            \"Tiruvarur\",\n" + "            \"Vellore\",\n" + "            \"Viluppuram\",\n" + "            \"Virudhunagar\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Telangana\",\n" + "         \"districts\":[  \n" + "            \"Adilabad\",\n" + "            \"Bhadradri Kothagudem\",\n" + "            \"Hyderabad\",\n" + "            \"Jagtial\",\n" + "            \"Jangaon\",\n" + "            \"Jayashankar Bhoopalpally\",\n" + "            \"Jogulamba Gadwal\",\n" + "            \"Kamareddy\",\n" + "            \"Karimnagar\",\n" + "            \"Khammam\",\n" + "            \"Komaram Bheem Asifabad\",\n" + "            \"Mahabubabad\",\n" + "            \"Mahabubnagar\",\n" + "            \"Mancherial\",\n" + "            \"Medak\",\n" + "            \"Medchal\",\n" + "            \"Nagarkurnool\",\n" + "            \"Nalgonda\",\n" + "            \"Nirmal\",\n" + "            \"Nizamabad\",\n" + "            \"Peddapalli\",\n" + "            \"Rajanna Sircilla\",\n" + "            \"Rangareddy\",\n" + "            \"Sangareddy\",\n" + "            \"Siddipet\",\n" + "            \"Suryapet\",\n" + "            \"Vikarabad\",\n" + "            \"Wanaparthy\",\n" + "            \"Warangal (Rural)\",\n" + "            \"Warangal (Urban)\",\n" + "            \"Yadadri Bhuvanagiri\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Tripura\",\n" + "         \"districts\":[  \n" + "            \"Dhalai\",\n" + "            \"Gomati\",\n" + "            \"Khowai\",\n" + "            \"North Tripura\",\n" + "            \"Sepahijala\",\n" + "            \"South Tripura\",\n" + "            \"Unakoti\",\n" + "            \"West Tripura\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Uttarakhand\",\n" + "         \"districts\":[  \n" + "            \"Almora\",\n" + "            \"Bageshwar\",\n" + "            \"Chamoli\",\n" + "            \"Champawat\",\n" + "            \"Dehradun\",\n" + "            \"Haridwar\",\n" + "            \"Nainital\",\n" + "            \"Pauri Garhwal\",\n" + "            \"Pithoragarh\",\n" + "            \"Rudraprayag\",\n" + "            \"Tehri Garhwal\",\n" + "            \"Udham Singh Nagar\",\n" + "            \"Uttarkashi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Uttar Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Agra\",\n" + "            \"Aligarh\",\n" + "            \"Allahabad\",\n" + "            \"Ambedkar Nagar\",\n" + "            \"Amethi (Chatrapati Sahuji Mahraj Nagar)\",\n" + "            \"Amroha (J.P. Nagar)\",\n" + "            \"Auraiya\",\n" + "            \"Azamgarh\",\n" + "            \"Baghpat\",\n" + "            \"Bahraich\",\n" + "            \"Ballia\",\n" + "            \"Balrampur\",\n" + "            \"Banda\",\n" + "            \"Barabanki\",\n" + "            \"Bareilly\",\n" + "            \"Basti\",\n" + "            \"Bhadohi\",\n" + "            \"Bijnor\",\n" + "            \"Budaun\",\n" + "            \"Bulandshahr\",\n" + "            \"Chandauli\",\n" + "            \"Chitrakoot\",\n" + "            \"Deoria\",\n" + "            \"Etah\",\n" + "            \"Etawah\",\n" + "            \"Faizabad\",\n" + "            \"Farrukhabad\",\n" + "            \"Fatehpur\",\n" + "            \"Firozabad\",\n" + "            \"Gautam Buddha Nagar\",\n" + "            \"Ghaziabad\",\n" + "            \"Ghazipur\",\n" + "            \"Gonda\",\n" + "            \"Gorakhpur\",\n" + "            \"Hamirpur\",\n" + "            \"Hapur (Panchsheel Nagar)\",\n" + "            \"Hardoi\",\n" + "            \"Hathras\",\n" + "            \"Jalaun\",\n" + "            \"Jaunpur\",\n" + "            \"Jhansi\",\n" + "            \"Kannauj\",\n" + "            \"Kanpur Dehat\",\n" + "            \"Kanpur Nagar\",\n" + "            \"Kanshiram Nagar (Kasganj)\",\n" + "            \"Kaushambi\",\n" + "            \"Kushinagar (Padrauna)\",\n" + "            \"Lakhimpur - Kheri\",\n" + "            \"Lalitpur\",\n" + "            \"Lucknow\",\n" + "            \"Maharajganj\",\n" + "            \"Mahoba\",\n" + "            \"Mainpuri\",\n" + "            \"Mathura\",\n" + "            \"Mau\",\n" + "            \"Meerut\",\n" + "            \"Mirzapur\",\n" + "            \"Moradabad\",\n" + "            \"Muzaffarnagar\",\n" + "            \"Pilibhit\",\n" + "            \"Pratapgarh\",\n" + "            \"RaeBareli\",\n" + "            \"Rampur\",\n" + "            \"Saharanpur\",\n" + "            \"Sambhal (Bhim Nagar)\",\n" + "            \"Sant Kabir Nagar\",\n" + "            \"Shahjahanpur\",\n" + "            \"Shamali (Prabuddh Nagar)\",\n" + "            \"Shravasti\",\n" + "            \"Siddharth Nagar\",\n" + "            \"Sitapur\",\n" + "            \"Sonbhadra\",\n" + "            \"Sultanpur\",\n" + "            \"Unnao\",\n" + "            \"Varanasi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"West Bengal\",\n" + "         \"districts\":[  \n" + "            \"Alipurduar\",\n" + "            \"Bankura\",\n" + "            \"Birbhum\",\n" + "            \"Burdwan (Bardhaman)\",\n" + "            \"Cooch Behar\",\n" + "            \"Dakshin Dinajpur (South Dinajpur)\",\n" + "            \"Darjeeling\",\n" + "            \"Hooghly\",\n" + "            \"Howrah\",\n" + "            \"Jalpaiguri\",\n" + "            \"Kalimpong\",\n" + "            \"Kolkata\",\n" + "            \"Malda\",\n" + "            \"Murshidabad\",\n" + "            \"Nadia\",\n" + "            \"North 24 Parganas\",\n" + "            \"Paschim Medinipur (West Medinipur)\",\n" + "            \"Purba Medinipur (East Medinipur)\",\n" + "            \"Purulia\",\n" + "            \"South 24 Parganas\",\n" + "            \"Uttar Dinajpur (North Dinajpur)\"\n" + "         ]\n" + "      }\n" + "   ]\n" + "}";
    private static final String TAG = Vender_RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender__registration);
        ButterKnife.bind(this);
        Checkout.preload(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("Check").equals("Splash")) {
                Confirmation_PopUP(SavedData.getService_Charge());
            }
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("fr");
        mAuth.useAppLanguage();
        getAllServices();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mobileNumberEt.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        getAll_State();
        stateSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Selected_State = adapterView.getItemAtPosition(i).toString();
                getAll_District(adapterView.getItemAtPosition(i).toString());

            }
        });
        districtSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Selected_district = adapterView.getItemAtPosition(i).toString();


            }
        });
        citySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Select_City = adapterView.getItemAtPosition(i).toString();


            }
        });

    }

    public void getAll_State() {
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(State);
            com.pixelmarketo.nrh.models.Example example = gson.fromJson(jsonObject.toString(), com.pixelmarketo.nrh.models.Example.class);
            for (int i = 0; i < example.getStates().size(); i++) {
                stateArrayList.add(example.getStates().get(i).getState());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stateArrayList);
            stateSpinner.setThreshold(1);
            stateSpinner.setAdapter(adapter);

        } catch (Exception e) {
        }
    }

    public void getAll_District(String City) {
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(State);
            com.pixelmarketo.nrh.models.Example example = gson.fromJson(jsonObject.toString(), com.pixelmarketo.nrh.models.Example.class);
            for (int i = 0; i < example.getStates().size(); i++) {
                if (example.getStates().get(i).getState().equals(City)) {
                    districtArrayList = (ArrayList<String>) example.getStates().get(i).getDistricts();
                }
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, districtArrayList);
            districtSpinner.setAdapter(spinnerArrayAdapter);
            districtSpinner.setThreshold(1);

            citySpinner.setAdapter(spinnerArrayAdapter);
            citySpinner.setThreshold(1);

        } catch (Exception e) {
        }
    }

    @OnClick({R.id.mobile_number_et, R.id.register_btn, R.id.upload_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mobile_number_et:
                Otp_PopUP();
                break;
            case R.id.register_btn:
                if (UserAccount.isEmpty(userNameEt, mobileNumberEt, tehsilEtv, addressEtv, pincodeEtv, passwordEtv, confirmPasswordEtv)) {
                    if (UserAccount.isPhoneNumberLength(mobileNumberEt)) {
                        if (passwordEtv.getText().toString().equals(confirmPasswordEtv.getText().toString())) {
                            if (!selectedImagePath.equals("")) {
                                if (termsConditionCheckbox.isChecked()) {
                                    RegisterCallwithImage();
                                } else {
                                    ErrorMessage.T(Vender_RegistrationActivity.this, " Check Terms & Condition First !");
                                }
                            } else {
                                ErrorMessage.T(Vender_RegistrationActivity.this, " Upload id Proof First !");
                            }
                        } else {
                            ErrorMessage.T(Vender_RegistrationActivity.this, "Password Mismatch!");
                        }

                    } else {
                        UserAccount.EditTextPointer.setError("Mobile Number Invalid !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;
            case R.id.upload_tv:
                selectImage();
                break;
        }
    }

    private void getAllServices() {
        if (NetworkUtil.isNetworkAvailable(Vender_RegistrationActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Vender_RegistrationActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.ServiceList();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            JSONObject object = null;
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("getAllOrders  response" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String Allresponse = object.toString();
                                Example example = gson.fromJson(Allresponse, Example.class);

                                if (example.getResult().size() > 0) {
                                    Vender_services_adapter myOrderAdapter = new Vender_services_adapter(Vender_RegistrationActivity.this, example.getResult(), "");
                                    LinearLayoutManager mLayoutManager = new GridLayoutManager(Vender_RegistrationActivity.this, 3);
                                    myServiceRv.setLayoutManager(mLayoutManager);
                                    myServiceRv.setItemAnimator(new DefaultItemAnimator());
                                    myServiceRv.setAdapter(myOrderAdapter);
                                }

                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exception" + e.toString());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    materialDialog.dismiss();

                }
            });


        } else {
            ErrorMessage.T(Vender_RegistrationActivity.this, "Internet Not Found! ");
        }

    }

    public void Otp_PopUP() {
        dialog = new Dialog(Vender_RegistrationActivity.this);
        dialog.setContentView(R.layout.input_otp_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextInputLayout mobile_textinput = (TextInputLayout) dialog.findViewById(R.id.mobile_textinput);
        mobilenumber_et = (EditText) dialog.findViewById(R.id.mobile_number_et);
        TextView title_tv = (TextView) dialog.findViewById(R.id.title_tv);
        TextView content_tv = (TextView) dialog.findViewById(R.id.content_tv);
        final LinearLayout layout_otp = (LinearLayout) dialog.findViewById(R.id.layout_otp);
        editTextone = (EditText) dialog.findViewById(R.id.editTextone);
        editTexttwo = (EditText) dialog.findViewById(R.id.editTexttwo);
        editTextthree = (EditText) dialog.findViewById(R.id.editTextthree);
        editTextfour = (EditText) dialog.findViewById(R.id.editTextfour);
        editTextfifth = (EditText) dialog.findViewById(R.id.editTextfifth);
        editTextsixth = (EditText) dialog.findViewById(R.id.editTextsixth);
        final Button submit_number_btn = (Button) dialog.findViewById(R.id.submit_number_btn);
        final Button submit_otp_btn = (Button) dialog.findViewById(R.id.submit_otp_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
        title_tv.setText("Enter Mobile Number");
        editTextone.addTextChangedListener(this);
        editTexttwo.addTextChangedListener(this);
        editTextthree.addTextChangedListener(this);
        editTextfour.addTextChangedListener(this);
        editTextfifth.addTextChangedListener(this);
        editTextsixth.addTextChangedListener(this);
        submit_number_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (UserAccount.isPhoneNumberLength(mobilenumber_et)) {
                    sendVerificationCode(mobilenumber_et.getText().toString().trim());
                    mobile_textinput.setVisibility(View.GONE);
                    layout_otp.setVisibility(View.VISIBLE);
                    submit_number_btn.setVisibility(View.GONE);
                    submit_otp_btn.setVisibility(View.VISIBLE);
                    title_tv.setText("Enter OTP");
                    content_tv.setText("Please type verification code");
                } else {
                    UserAccount.EditTextPointer.setError("Mobile Number Invalid !");
                    UserAccount.EditTextPointer.requestFocus();
                }


            }
        });
        submit_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserAccount.isEmpty(editTextone, editTexttwo, editTextthree, editTextfour, editTextfifth, editTextsixth)) {
                    verifyVerificationCode(editTextone.getText().toString() + editTexttwo.getText().toString() + editTextthree.getText().toString() + editTextfour.getText().toString() + editTextfifth.getText().toString() + editTextsixth.getText().toString());
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile, 60, TimeUnit.SECONDS, Vender_RegistrationActivity.this, mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                String[] part = code.split("");
                editTextone.setText(part[0]);
                editTexttwo.setText(part[1]);
                editTextthree.setText(part[2]);
                editTextfour.setText(part[3]);
                editTextfifth.setText(part[4]);
                editTextsixth.setText(part[5]);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Vender_RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("FirebaseException", "" + e.getMessage());

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(Vender_RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //verification successful we will start the profile activity
                    mobileNumberEt.setText(mobilenumber_et.getText().toString());
                    dialog.dismiss();
                } else {
                    //verification unsuccessful.. display an error message
                    String message = "Somthing is wrong, we will fix it soon...";
                    ErrorMessage.E("Else" + task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }
                    Toast.makeText(Vender_RegistrationActivity.this, message, Toast.LENGTH_LONG).show();
                    Log.e("task", "" + message);
                           /* Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();*/
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        {
            if (editable.length() == 1) {
                if (editTextone.length() == 1) {
                    editTexttwo.requestFocus();
                }
                if (editTexttwo.length() == 1) {
                    editTextthree.requestFocus();
                }
                if (editTextthree.length() == 1) {
                    editTextfour.requestFocus();
                }
                if (editTextfour.length() == 1) {
                    editTextfifth.requestFocus();
                }
                if (editTextfifth.length() == 1) {
                    editTextsixth.requestFocus();
                }
            } else if (editable.length() == 0) {
                if (editTextsixth.length() == 0) {
                    editTextfifth.requestFocus();
                }
                if (editTextfifth.length() == 0) {
                    editTextfour.requestFocus();
                }
                if (editTextfour.length() == 0) {
                    editTextthree.requestFocus();
                }
                if (editTextthree.length() == 0) {
                    editTexttwo.requestFocus();
                }
                if (editTexttwo.length() == 0) {
                    editTextone.requestFocus();
                }
            }
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"From Camera", "From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Vender_RegistrationActivity.this);
        builder.setTitle("Please choose an Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("From Camera")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkCameraPermission()) cameraIntent();
                        else requestPermission();
                    } else cameraIntent();
                    //   cameraIntent();
                } else if (options[item].equals("From Gallery")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkGalleryPermission()) galleryIntent();
                        else requestGalleryPermission();
                    } else galleryIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }


    private void galleryIntent() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Vender_RegistrationActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(Vender_RegistrationActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
    }

    private boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(Vender_RegistrationActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(Vender_RegistrationActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK && null != data) {
            Uri cameraURI = data.getData();
            Bitmap productImageBitmap = (Bitmap) data.getExtras().get("data");
            //proffileImage.setImageBitmap(productImageBitmap);
            Uri tempUri = getImageUri(Vender_RegistrationActivity.this, productImageBitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(getRealPathFromURI(tempUri));
            selectedImagePath = getRealPathFromURI(tempUri);
            ErrorMessage.E("selectedImagePath Camera" + selectedImagePath);
            ErrorMessage.E("finalFile Camera" + finalFile);

            uploadTv.setText(selectedImagePath);
            uploadTv.setBackground(getResources().getDrawable(R.drawable.ic_corner_green));

        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            Uri galleryURI = data.getData();
            // proffileImage.setImageURI(galleryURI);
            selectedImagePath = getRealPathFromURIPath(galleryURI, Vender_RegistrationActivity.this);
            System.out.println("===========selectedImagePath on acti rest : " + selectedImagePath);

            uploadTv.setText(selectedImagePath);
            uploadTv.setBackground(getResources().getDrawable(R.drawable.ic_corner_green));
            /*finalFile = new File(selectedImagePath);*/
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = null;
        int idx = 0;
        String s = "";
        try {
            cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                s = cursor.getString(idx);
            }
        } catch (IllegalStateException e) {
            Log.e("Exception image", "selected " + e.toString());
        }

        return s;

    }

    public Uri getImageUri(FragmentActivity inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (Vender_RegistrationActivity.this.getContentResolver() != null) {
            Cursor cursor = Vender_RegistrationActivity.this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void RegisterCallwithImage() {
        if (NetworkUtil.isNetworkAvailable(Vender_RegistrationActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Vender_RegistrationActivity.this);
            File file = new File(selectedImagePath);
            final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("id_proof", file.getName(), requestfile);

            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.register_vendor(userNameEt.getText().toString(), mobileNumberEt.getText().toString().trim(), Selected_State, Selected_district, tehsilEtv.getText().toString(), Select_City, addressEtv.getText().toString(), pincodeEtv.getText().toString(), passwordEtv.getText().toString(), service_idsList, body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("=======Register code:" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String responseData = response.body().string();
                                ErrorMessage.T(Vender_RegistrationActivity.this, object.getString("message"));
                                JSONObject jsonObject1 = object.getJSONObject("result");
                                UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setDisplayName(jsonObject1.getString("fullname"));
                                userProfileModel.setUid(jsonObject1.getString("token"));
                                userProfileModel.setProvider(jsonObject1.getString("district"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("state"));
                                userProfileModel.setProfile_pic(jsonObject1.getString("city"));
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                userProfileModel.setProfile_pic("");
                                userProfileModel.setUserVenderCode(jsonObject1.getString("vendor_code"));
                                userProfileModel.setUserType("Vender");
                                SavedData.savePayment_Status(jsonObject1.getString("status"));
                                SavedData.saveService_Charge(String.valueOf(Double.parseDouble(jsonObject1.getString("service_charge")) * service_idsList.size()));
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                Confirmation_PopUP(String.valueOf(Double.parseDouble(jsonObject1.getString("service_charge")) * service_idsList.size()));
                                //ErrorMessage.I(Vender_RegistrationActivity.this, VerifyDocumentActivity.class, null);

                                materialDialog.dismiss();
                            } else {
                                materialDialog.dismiss();
                                ErrorMessage.T(Vender_RegistrationActivity.this, object.getString("message"));
                            }
                        } catch (JSONException e) {
                            materialDialog.dismiss();
                            e.printStackTrace();
                            ErrorMessage.E("========json Ex  :" + e.getMessage());
                            System.out.println("========json Ex  :" + e.toString());
                        } catch (Exception e) {
                            materialDialog.dismiss();
                            e.printStackTrace();
                            ErrorMessage.E("========io Ex  :" + e);
                        }
                    } else {
                        Log.e("response fail", "response fail");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("error on failure" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(Vender_RegistrationActivity.this, "No Internet");
        }
    }

    public void GET_ServiceId(String serviceId) {
        boolean is_Exist=true;
        if (service_idsList.size() > 0) {
            for (int i = 0; i < service_idsList.size(); i++) {
                if (service_idsList.get(i).equals(serviceId)){
                    is_Exist=false;
                }
            }
            if (is_Exist) {
                service_idsList.add(serviceId);
            }
        } else {
            service_idsList.add(serviceId);
        }

    }
    public void remove_item(String service_id){
        if (service_idsList.size() > 0) {
            service_idsList.remove(service_id);
        }

    }

    public void Confirmation_PopUP(String service_charge) {
        Dialog dialog = new Dialog(Vender_RegistrationActivity.this);
        dialog.setContentView(R.layout.payment_confirmation_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button submit_btn = (Button) dialog.findViewById(R.id.submit_payment_btn);
        submit_btn.setText("Pay ₹ " + service_charge);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                if (service_charge.equals("0") || service_charge.equals("00")) {
                    ConfirmPaymentOnServer(" No Amount");
                } else {
                    startPayment(("" + (new DecimalFormat("#").format(Double.parseDouble(service_charge))) + "00"));
                }
            }
        });
        dialog.show();
    }

    private void ConfirmPaymentOnServer(String transcation_id) {
        if (NetworkUtil.isNetworkAvailable(Vender_RegistrationActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Vender_RegistrationActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.conformation_payment(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUid(), transcation_id, "OnlinePayment");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                SavedData.savePayment_Status("1");
                                ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(Vender_RegistrationActivity.this, object.getString("message"));
                                ErrorMessage.I(Vender_RegistrationActivity.this, VenderDashboardActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(Vender_RegistrationActivity.this, object.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(Vender_RegistrationActivity.this, "No Internet");
        }
    }

    public void startPayment(String Charge) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        ErrorMessage.E("Charge startPayment" + Charge);
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "NRS");
            options.put("description", "Vender Subscription");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", getResources().getDrawable(R.drawable.ic_launcher));
            options.put("currency", "INR");
            options.put("amount", Charge);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        ConfirmPaymentOnServer(s);
    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    private void RegisterCallwithoutImage() {
        if (NetworkUtil.isNetworkAvailable(Vender_RegistrationActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Vender_RegistrationActivity.this);
            File file = new File(selectedImagePath);
            final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("id_proof", file.getName(), requestfile);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.register_vendor_without_image(userNameEt.getText().toString(), mobileNumberEt.getText().toString().trim(), Selected_State, Selected_district, tehsilEtv.getText().toString(), Select_City, addressEtv.getText().toString(), pincodeEtv.getText().toString(), passwordEtv.getText().toString(), service_idsList);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("=======Register code:" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String responseData = response.body().string();
                                ErrorMessage.T(Vender_RegistrationActivity.this, object.getString("message"));
                                JSONObject jsonObject1 = object.getJSONObject("result");
                                UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setDisplayName(jsonObject1.getString("fullname"));
                                userProfileModel.setUid(jsonObject1.getString("token"));
                                userProfileModel.setProvider(jsonObject1.getString("district"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("state"));
                                userProfileModel.setProfile_pic(jsonObject1.getString("city"));
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                userProfileModel.setProfile_pic("");
                                userProfileModel.setUserVenderCode(jsonObject1.getString("vendor_code"));
                                userProfileModel.setUserType("Vender");
                                SavedData.savePayment_Status(jsonObject1.getString("status"));
                                SavedData.saveService_Charge(String.valueOf(Double.parseDouble(jsonObject1.getString("service_charge")) * service_idsList.size()));
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                Confirmation_PopUP(String.valueOf(Double.parseDouble(jsonObject1.getString("service_charge")) * service_idsList.size()));
                                //ErrorMessage.I(Vender_RegistrationActivity.this, VerifyDocumentActivity.class, null);

                                materialDialog.dismiss();
                            } else {
                                materialDialog.dismiss();
                                ErrorMessage.T(Vender_RegistrationActivity.this, object.getString("message"));
                            }
                        } catch (JSONException e) {
                            materialDialog.dismiss();
                            e.printStackTrace();
                            ErrorMessage.E("========json Ex  :" + e.getMessage());
                            System.out.println("========json Ex  :" + e.toString());
                        } catch (Exception e) {
                            materialDialog.dismiss();
                            e.printStackTrace();
                            ErrorMessage.E("========io Ex  :" + e);
                        }
                    } else {
                        Log.e("response fail", "response fail");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("error on failure" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(Vender_RegistrationActivity.this, "No Internet");
        }
    }

}
