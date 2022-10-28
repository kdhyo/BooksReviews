package godofjava2nd.Chapter26;

import java.io.File;
import java.text.DecimalFormat;

public class FileSizeSummary {

    private final DecimalFormat formmater;

    public FileSizeSummary() {
        String pattern = "#,##0.0#";
        formmater = new DecimalFormat(pattern);
    }

    public static void main(String[] args) {
        FileSizeSummary sample = new FileSizeSummary();
        String path = "D:\\nextstep";
        long sum = sample.printFileSize(path);
        System.out.println(path + "'s total size=" + sample.convertFileLength(sum));
    }

    public long printFileSize(String dirName) {
        File dir = new File(dirName);
        long sum = 0;

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    String tempFileName = file.getAbsolutePath();
                    long fileLength = tempFileName.length();
                    System.out.println(tempFileName + " = " + this.convertFileLength(fileLength));
                    sum += fileLength;
                } else {
                    String tempDirName = file.getAbsolutePath();
                    long fileLength = printFileSize(tempDirName);
                    System.out.println("[" + tempDirName + "] = " + this.convertFileLength(fileLength));
                    sum += fileLength;
                }
            }
        }

        return sum;
    }

    private static final long KB = 1024;
    private static final long MB = KB * KB;
    private static final long GB = KB * KB * KB;

    private String convertFileLength(long fileLength) {
        if (fileLength <= KB) {
            return fileLength + " b";
        }

        if (fileLength <= MB) {
            
            return formmater.format((1.0 * fileLength) / KB) + " kb";
        }

        if (fileLength <= GB) {
            return formmater.format((1.0 * fileLength) / MB) + " kb";
        }

        return formmater.format((1.0 * fileLength) / GB) + " gb";
    }

}
