package functional_tests;

import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionalTest {

    public class MockBlockChain {

    }

    @Test
    public void testPatientCanPublishData() {

        /**
         * Alice wants to publish her EHR on Hyper-DCPABE
         * She runs the jar application on Desktop
         */

        // She agree to receive a pair of blockchain keys

        // The keys are printed on the console so she can save them on a secure database

        // After she kept the keys, the client asks for name and e-mail and publish them on the chain

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

        assertTrue("Finish the test!", false);
    }

    @Test
    public void testAuthorizedUserCanRecoverPatientRecords(){
        // other patient request access to blockchain

        // container.obterPaciente() returns Paciente

        // cliente 2 java lê prontuários[rand] e pede pro servidor o arquivo, enviando código como post

        // serv devolve o arquivo teste

        // cliente 2 java vai decifrar teste e envia o hash via post ao serv

        // serv verifica o hash enviado, e devolve o arquivo pdf encriptado

        // cliente 2 java decifra o arquivo pdf
        assertTrue("Finish the test!", false);
    }
}