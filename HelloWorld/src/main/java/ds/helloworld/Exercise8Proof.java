package ds.helloworld;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


class Exercise8Proof{

    // Returns a hex string given an array of bytes
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void main(String[] args){
        String end = ",4,19,Pink,Orange,002fdb16086d97e03613fa0caa87b280eca956216e61a35400408bdd3a449e45";

        boolean go = true;

        int i = 0;

        while(go){
            String in = (String.valueOf(i) + end);

            // First generate digits
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            md.update(in.getBytes());

            String hash = bytesToHex(md.digest());
            if(hash.substring(0,2).equals("00") | hash.substring(0,4).equals("0000")){
                go = false;
                System.out.println(in);
                System.out.println(hash);
            }
            i+=1;
        }

    }
}
