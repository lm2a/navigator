# navigator
Navigator is a MVC pattern implementation (similar to Struts framework for web) for Android apps which are based in an UI structure composed for one Activity and several Fragments.

Using this framework you can avoid boiler plate code to navigate among fragments and after configure your screen flow in a xml file located under /res/raw. Which should be a wonderful source of documentation about your app.

Here you have an image showing the way it works:

![Navigator](https://photos.app.goo.gl/j698oJf9orTsoRru2)

After that all the screen flows should apper in a xml file like this:

<?xml version="1.0" encoding="utf-8"?>
<navigator fragmentVersion="V4">
    <state
        addToBackStack="false"
        currentScreen="LANGUAGE"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.SelectLanguageFragment">
        <!--  lm2a modificada para ir directamente a Transmission reemplace PERSONALIZATION_QR por PERSONALIZATION -->
        <!--  lm2a agregue flowGenericTable para definir el flujo (si la mesa es generica o debe haber una por cada eleccion) -->
        <property
            name="flowGenericTable"
            type="boolean"
            value="true" />
        <event
            eventName="LANGUAGE_SELECTED"
            nextScreen="AUTOTEST" />
        <event
            eventName="ADMIN_LAUNCHED"
            nextScreen="ADMIN" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="AUTOTEST"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.AutotestFragment">
        <event
            eventName="AUTOTEST_COMPLETED"
            nextScreen="MAINVIEW" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="MAINVIEW"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.PSDetailFragment">
        <event eventName="BACK" />
        <event
            eventName="PERSONALIZATION_INIT"
            nextScreen="PERSONALIZATION_QR" />
        <event
            eventName="ADMIN_LAUNCHED"
            nextScreen="ADMIN" />
        <event
            eventName="IDENTIFY_VOTER"
            nextScreen="VOTER_SEARCHER" />
        <event
            eventName="SCAN_IDENTIFY_HW"
            nextScreen="READER_DOCUMENT_HONEYWELL" />
        <event
            eventName="SCAN_IDENTIFY_IN"
            nextScreen="READER_DOCUMENT_INDRA" />
        <event
            eventName="REPORT_READER"
            nextScreen="REPORT_READER" />
        <event
            eventName="TRANSMISSION"
            nextScreen="TOTALES_MESA" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="VOTER_SEARCHER"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.VoterSearcherFragment">
        <event
            eventName="SEARCH_CANCELED"
            nextScreen="MAINVIEW" />
        <event
            eventName="SEARCH_OK"
            nextScreen="VOTER_DETAIL" />
        <event
            eventName="REGISTER"
            nextScreen="REGISTRATION" />
        <event
            eventName="READER_DOCUMENT_HONEYWELL"
            nextScreen="READER_DOCUMENT_HONEYWELL" />
        <event
            eventName="READER_DOCUMENT_INDRA"
            nextScreen="READER_DOCUMENT_INDRA" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="READER_DOCUMENT_INDRA"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.ReaderDocumentIndraFragment">
        <event
            eventName="READ_OK"
            nextScreen="VOTER_DETAIL" />
        <event
            eventName="READ_FAIL"
            nextScreen="READER_RESULT" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="READER_DOCUMENT_HONEYWELL"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.ReaderDocumentHoneywellFragment">
        <event
            eventName="READ_OK"
            nextScreen="VOTER_DETAIL" />
        <event
            eventName="READ_FAIL"
            nextScreen="READER_RESULT" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="READER_RESULT"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.ReaderDocumentResultFragment">
        <event
            eventName="REGISTER"
            nextScreen="REGISTRATION" />
        <event
            eventName="SEARCH"
            nextScreen="VOTER_SEARCHER" />
        <event
            eventName="RETRY_INDRA"
            nextScreen="READER_DOCUMENT_INDRA" />
        <event
            eventName="RETRY_HONEYWELL"
            nextScreen="READER_DOCUMENT_HONEYWELL" />
        <event
            eventName="CANCEL"
            nextScreen="MAINVIEW" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="VOTER_DETAIL"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.VoterDetailFragment">
        <event
            eventName="CANCELED"
            nextScreen="VOTER_SEARCHER" />
        <event
            eventName="CONTINUE"
            nextScreen="READER_FINGERPRINT" />
        <event
            eventName="FP_CAPTURING"
            nextScreen="FP_RECORD" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="READER_FINGERPRINT"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.ReaderFingerprintFragment">
        <event
            eventName="BACK"
            nextScreen="VOTER_DETAIL" />
        <event
            eventName="MATCH_OK"
            nextScreen="VOTER_RESULT" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="VOTER_RESULT"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.VoterResultMessageFragment">
        <event
            eventName="CONTINUE"
            nextScreen="MAINVIEW" />
    </state>
    <state
        addToBackStack="true"
        currentScreen="ELECTIONS"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.transmission.ElectionsFragment">
        <event
            eventName="ELECTION_CHOOSED"
            nextScreen="RESULTS" />
        <event
            eventName="GO_TO_MAIN"
            nextScreen="MAINVIEW" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="TOTALES_MESA"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.transmission.TableFragment">
        <event
            eventName="ACEPTED_TO_RESULTS"
            nextScreen="RESULTS" />
        <event
            eventName="ACEPTED_TO_ELECTIONS"
            nextScreen="ELECTIONS" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="RESULTS"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.transmission.ResultsFragment">
        <event
            eventName="ACCEPT"
            nextScreen="VIDEOINSTRUCTIONS" />
        <event
            eventName="WATCH"
            nextScreen="PREVIEW" />
        <event eventName="BACK" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="VIDEOINSTRUCTIONS"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.transmission.VideoinstructionsFragment">
        <event
            eventName="SHOWED"
            nextScreen="CAMERA" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="BARCODE"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.transmission.BarcodeFragment">
        <event
            eventName="READING_OK"
            nextScreen="CAMERA" />
        <event
            eventName="READING_NOK"
            nextScreen="BARCODE" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="CAMERA"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.transmission.CameraFragment">
        <event
            eventName="PHOTO_OK"
            nextScreen="ELECTIONS" />
        <event
            eventName="PHOTO_NOK"
            nextScreen="CAMERA" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="PREVIEW"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.transmission.PreviewFragment">
        <event
            eventName="PHOTO_ACEPTED"
            nextScreen="ELECTIONS" />
        <event
            eventName="PHOTO_REJECTED"
            nextScreen="CAMERA" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="PERSONALIZATION_QR"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.personalization.PersonalizationQrFragment">
        <event
            eventName="ACCEPT"
            nextScreen="PERSONALIZATION" />
        <event
            eventName="CANCEL"
            nextScreen="MAINVIEW" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="PERSONALIZATION"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.personalization.PersonalizationFragment">

        <event
            eventName="ACCEPT"
            nextScreen="PERSONALIZATION_END" />
        <event
            eventName="TMPFLOWGEN"
            nextScreen="TOTALES_MESA" />
        <event
            eventName="TMPFLOWNOGEN"
            nextScreen="ELECTIONS" />
        <event
            eventName="CANCEL"
            nextScreen="MAINVIEW" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="PERSONALIZATION_END"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.personalization.PersonalizationEndFragment">
        <event
            eventName="ACCEPT"
            nextScreen="MAINVIEW" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="ADMIN"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.administration.AdminFragment">
        <event eventName="BACK" />
        <event
            eventName="LANGUAGE"
            nextScreen="LANGUAGE" />
        <event
            eventName="SETTINGS_LAUNCHED"
            nextScreen="SETTINGS" />
        <event
            eventName="STATE_CHANGED"
            nextScreen="MAINVIEW" />
        <event
            eventName="REGISTRATION_LAUNCHED"
            nextScreen="REGISTRATION" />
        <event
            eventName="VOTERS_LIST_LAUNCHED"
            nextScreen="VOTERS_LIST" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="SETTINGS"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.administration.SettingsFragment">
        <event eventName="BACK" />
        <event
            eventName="ADMIN_LAUNCHED"
            nextScreen="ADMIN" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="REGISTRATION"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.registration.RegistrationFragment">
        <event eventName="BACK" />
        <event
            eventName="MATCH_OK"
            nextScreen="VOTER_RESULT" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="FP_RECORD"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.registration.FingerprintRecordingFragment">
        <event eventName="BACK" />
        <event
            eventName="MATCH_OK"
            nextScreen="VOTER_RESULT" />
    </state>
    <state
        addToBackStack="false"
        currentScreen="VOTERS_LIST"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.registration.VotersListFragment">
        <event eventName="BACK" />
        <event
            eventName="VIEWER"
            nextScreen="REGISTRATION" />
        <event
            eventName="REPORT_READER"
            nextScreen="REPORT_READER" />
    </state>

    <state
        addToBackStack="false"
        currentScreen="REPORT_READER"
        method="REPLACE"
        nameClass="com.lm2a.poll.pkg.fragment.identifier.ReportReaderFragment">
        <event
            eventName="BACK"/>
    </state>

</navigator>

In order to use it you should add this to your project as a module and then initiate it in your Main Activity onCreate method in this way:

        mNavigator = Navigator.getInstance();
        mNavigator.init(this, R.id.container);
       
       To Navigate to the first Fragment in your Main Activity you should use 
       mNavigator.next("LANGUAGE");
       
       In all the next fragment you should use
       Navigator.getInstance().next("ELECTIONS", "ELECTION_CHOOSED");      
       
        
Flujo de pantallas
------------------

Considerando que el flujo de pantalla puede cambiar segun los datos de mesa sean comunes a todas las elecciones o no
he creado una clase Config con una variable booleana que define el flujo. Este objeto se encuentra incluido en el Singleton (God)

Asi mismo el valor a la variable se lo estoy pasando provisoriamente mediante un property en navigator.xml

    <state
        nameClass="com.lm2a.poll.pkg.fragment.identifier.SelectLanguageFragment"
        currentScreen="LANGUAGE"
        method="REPLACE"
        addToBackStack="false">
        <!--  lm2a modificada para ir directamente a Transmission reemplace PERSONALIZATION_QR por PERSONALIZATION -->
        <!--  lm2a agregue flowGenericTable para definir el flujo (si la mesa es generica o debe haber una por cada eleccion) -->
        <property name="flowGenericTable" value="false" type="boolean" />
        <event
            nextScreen="PERSONALIZATION"
            eventName="LANGUAGE_SELECTED" />
    </state>

Fragmentos en Navigator
-----------------------

Todos los fragmentos ahora deben extender de BaseFragment. Esta clase abstracta proporciona conexion con ButterKnife y evita la
implementacion del metodo onCreateView ya que basta con implementar

    protected abstract int getFragmentLayout();

    pasandole el layout correspondiente.

Importante: Para que los widgets se instancien con ButterKnife es importante llamar en las implementaciones al metodo onViewCreated
de la clase madre que es la que obtiene la referencia a ButterKnife. Esto es, agregar

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



Navigator, nuevas caracteristicas
---------------------------------

Agregue en Navigator la posibilidad de recuperar:
- cual fue la pantalla anterior,
- cual evento condujo a la actual
- y cual es la pantalla actual. Este metodo esta hecho y funcionando. Actualmente los fragmentos que heredan de BaseFragment pueden utilizar los metodos:

getLastScreenBeforeCurrent():
getCurrentScreen():
getEventLeadingToCurrentScreen():

No obstante deberiamos tratar de evitar logica de navegacion fuera de la que debe estar implementada en el XML ya que se nos espaguetizaria el codigo teniendolo mitad en un lado y mitad en el otro.

Para volver hacia atras he implementado el metodo Navigator.getInstance().goBack() pero lo he hecho privado para que se lo llame agregando un tag event BACK como muestra. El motivo de esto
es que una de las finalidades del Navigator es que el XML sirva de documentacion sobre la navegacion y para ello es necesario que se refleje
en que pantalla se vuelve atras.

La llamada entonces se hace como

        Navigator.getInstance().next("RESULTS", Navigator.BACK_EVENT);

Y el XML debe tener la entrada como sigue

    <state
        nameClass="com.lm2a.poll.pkg.fragment.transmission.ResultsFragment"
        currentScreen="RESULTS"
        method="REPLACE"
        addToBackStack="false">
        <event
            nextScreen="ELECTIONS"
            eventName="ACCEPT" />
        <event
            eventName="BACK" />
    </state>

    En caso contrario aparecera en los Logs un error del tipop:

    08-14 12:23:01.459 7376-7376/com.lm2a.poll.pkg E/I&D: 1 -  Error RESULTS intenta hacer BACK pero no tiene el evento definido

Este metodo vuelve atras, pasando en el Bundle, los parametros de pantalla anterior: cual evento condujo a la actual (BACK) y cual es la pantalla actual. Y ejecutando el metodo
updateUserInterface("BACK");
Donde BACK es una constante generica definida en Java en el Navigator  (Navigator.BACK_EVENT) que identifica el volver a la pantalla anterior.


Example for a BaseFragment implementing Navigator
-------------------------------------------------

public abstract class BaseFragment extends Fragment implements NavegableFragment {

    private NavegableFragment me;
    private Bundle mProperties;
    private String mEventUIConfigurationId;
    private String mLastScreenBeforeCurrent;
    private String mEventLeadingToCurrentScreen;
    private String mCurrentScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getFragmentLayout()==0){
            return null;
        }else{
            return inflater.inflate(getFragmentLayout(), container, false);
        }
    }

    public BaseFragment() {
        me = this;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        try {
            MainActivity.instance.initAdminAccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract int getFragmentLayout();

    @Override
    public NavegableFragment getInstance() {
        return me;
    }

    @Override
    public void updateUserInterface(String event) {
        mEventUIConfigurationId = event;
    }

    @Override
    public void onSetProperties(Bundle bundle) {
        mProperties = bundle;
        //getting Navigator internal data
        mLastScreenBeforeCurrent = bundle.getString(Navigator.COME_FROM_SCREEN);
        mEventLeadingToCurrentScreen = bundle.getString(Navigator.BY_EVENT);
        mCurrentScreen = bundle.getString(Navigator.CURRENT_SCREEN);
    }

    public void changeTitle(int strId, boolean isTitleVisible) {
        String packageName = "android";
        int resId = getResources().getIdentifier("action_bar_container", "id", packageName);
        View view = getActivity().findViewById(resId);

        TitleUtil.changeTitle(view, strId, isTitleVisible);
    }

    @Override
    public String getEventUIConfigurationId() {
        return mEventUIConfigurationId;
    }

    @Override
    public String getLastScreenBeforeCurrent() {
        return mLastScreenBeforeCurrent;
    }

    @Override
    public String getEventLeadingToCurrentScreen() {
        return mEventLeadingToCurrentScreen;
    }

    @Override
    public String getCurrentScreen() {
        return mCurrentScreen;
    }
}
//-lm2a-/

//lm2a



