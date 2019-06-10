package functionaltests;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.*;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brunoarruda.hyper_dcpabe.Client;

public class FunctionalTest {

    private InputStream stdin;
    private static ConsoleOutputCapturer systemOutput;

    @BeforeClass
    public static void beforeAll() {
        systemOutput = new ConsoleOutputCapturer();
    }

    @Before
    public void setUp() {
        stdin = System.in;
        systemOutput.start();
    }

    @After
    public void tearDown() {
        System.setIn(stdin);
    }

    @Test
    public void testPatientCanPublishData() {

        /**
         * Alice wants to publish her EHR on Hyper-DCPABE
         * She runs the jar application on Desktop
         */

        Client client = new Client();
        client.runOnConsole();

        // She agree to receive a pair of blockchain keys
        Util.simulateUserInput("yes");

        // The keys are printed on the console so she can save them on a secure database
        String response = systemOutput.stop();
        
        assertThat(response, CoreMatchers.containsString("private key: (.*?)$"));
        assertThat(response, CoreMatchers.containsString("public key: (.*?)$"));
        // After she kept the keys, the client asks for name and e-mail and publish them on the chain
        fail("Finish the test!");
        
        /**
         * Alice asks on blockchain for public keys of attributes "CRM" and "CFM" to make
         * the basic encryption "authorized or CRM or CFM".
         *
         * Obs: CFM and CRM means in Brazil respectively Federal an Regional Council
         * of Medicine.
         */

        // she receives the public key of attributes CRM and CFM after sometime.

        /**
         * she creates the attribute "authorized", and send its public key to the
         * blockchain
         */

        // with all keys, she encrypts the data: a PDF document.

        /**
         * The client digests the hash of the resulting file and encrypt with the same
         * policy used on that.
         */

        // She agrees to client sending both files to a known server

        // The client receives from the server an key that should be used to find the file

        /**
         * Finally, its published in blockchain a transaction containing:
         * 1. the address of the transaction with her personal data
         * 2. an JSON object, encrypted with the same policy used to encrypt the
         * sent file, containing the server address of the file and the code to
         * find the file.
         */
    }

    @Test
    public void testAuthorizedUserCanRecoverPatientRecords(){
        // other patient request access to blockchain
        fail("Finish the test!");
        // container.obterPaciente() returns Paciente

        // cliente 2 java lê prontuários[rand] e pede pro servidor o arquivo, enviando código como post

        // serv devolve o arquivo teste

        // cliente 2 java vai decifrar teste e envia o hash via post ao serv

        // serv verifica o hash enviado, e devolve o arquivo pdf encriptado

        // cliente 2 java decifra o arquivo pdf

    }
}