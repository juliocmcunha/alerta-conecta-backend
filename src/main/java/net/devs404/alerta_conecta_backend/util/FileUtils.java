package net.devs404.alerta_conecta_backend.util;

import java.util.Random;
import java.util.UUID;

public class FileUtils 
{
	public static boolean isValidImageExtension(String extension) 
	{
        String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".webp", ".bmp"};
        for (String allowedExt : allowedExtensions) 
        {
            if (allowedExt.equalsIgnoreCase(extension)) 
            {
                return true;
            }
        }
        return false;
    }
	
	public static String getFileExtension(String fileName) 
	{
        if (fileName == null || fileName.lastIndexOf(".") == -1) 
        {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
	
	public static String generateUniqueFolderName() 
	{
		String baseName = "";
		Random rnd = new Random();
		for(int i = 0; i < 6; i++)
		{
			baseName = baseName.concat(new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"}[rnd.nextInt(10)]);
		}
		
        String uniqueID = UUID.randomUUID().toString();
        return baseName + "_" + uniqueID;
    }
	
	public static String transformUniqueFileName(String originalFileName) 
	{
        String fileExtension = getFileExtension(originalFileName);
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String uniqueID = UUID.randomUUID().toString();
        return baseName + "_" + uniqueID + fileExtension;
    }
}
