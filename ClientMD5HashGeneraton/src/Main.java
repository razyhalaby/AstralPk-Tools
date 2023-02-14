import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Scanner;

public class Main
{
      private static final File CLIENT_FOLDER_PATH = new File(System.getProperty("user.home") + "/AstralPk client/");

      public static void main(String[] args)
      {
            Scanner input = new Scanner(System.in);

            File clientFile = getLatestLocalClient();
            if (clientFile == null)
            {
                  input.nextLine();
                  return;
            }

            try
            {
                  StringBuilder MD5Code = new StringBuilder("md5:");
                  MD5Code.append(getMD5Checksum(clientFile));
                  System.out.println(MD5Code);
            }
            catch (Exception e)
            {
                  System.out.println("Failed to generate the MD5 from the client");
            }

            input.nextLine();
      }

      private static File getLatestLocalClient()
      {
            File[] filesInClientFolder = CLIENT_FOLDER_PATH.listFiles();

            if (filesInClientFolder != null)
            {
                  // Loop through all files in the folder
                  for (File currentFile : filesInClientFolder)
                  {
                        // Check if the file equals to astralpk.jar
                        if (currentFile.getName().equals("AstralPk.jar"))
                        {
                              return currentFile;
                        }
                  }
            }
            else
            {
                  System.out.println("Failed to list files inside: " + CLIENT_FOLDER_PATH.getAbsolutePath());
            }

            System.out.println("Client AstralPK.jar does not exist in the folder " + CLIENT_FOLDER_PATH.getAbsolutePath());

            return null;
      }

      private static String getMD5Checksum(File file) throws Exception
      {
            byte[] b = createChecksum(file.getAbsolutePath());
            String result = "";
            for (int i = 0; i < b.length; i++)
                  result = result + Integer.toString((b[i] & 0xFF) + 256, 16).substring(1);
            return result;
      }

      private static byte[] createChecksum(String filename) throws Exception
      {
            InputStream fis = new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            while (true)
            {
                  int numRead = fis.read(buffer);
                  if (numRead > 0)
                  {
                        complete.update(buffer, 0, numRead);
                  }
                  if (numRead == -1)
                  {
                        fis.close();
                        return complete.digest();
                  }
            }
      }
}