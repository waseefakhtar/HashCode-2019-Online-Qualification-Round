package com.example.hashcode;

import org.omg.CORBA.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.NativeMath.min;

public class HashCode {

    public static void main(String[] arg) throws Exception {

        File myFile = new File("a_example.txt");
        Scanner input = new Scanner(myFile);
        String in = "";
        int numberOfImages = Integer.valueOf(input.nextLine());
        String imagesDescription[] = new String[numberOfImages];
        int index = 0;
        while (input.hasNext()) {
            in = input.nextLine();
            //System.out.println(in);
            imagesDescription[index] = in;
            index++;
        }

        Image images[] = new Image[numberOfImages];
        int imageIndex = 0;
        for (String imageDescription: imagesDescription) {
            char charArray[] = imageDescription.replace(" ", "").toCharArray();
            char imageMode = charArray[0];
            int numberOfTags = Character.getNumericValue(charArray[1]);
            //System.out.println(imageMode);
            //System.out.println(numberOfTags);
            String tags[] = imageDescription.substring(4).split(" ");
            for (String tag: tags) {
                //System.out.println(tag);
            }

            Image image = new Image();
            image.index = imageIndex;
            image.imageMode = imageMode;
            image.numberOfTags = numberOfTags;
            image.tags = tags;

            images[imageIndex] = image;
            imageIndex++;
        }

        ArrayList<ImageList> imageListArrayList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            for (int j = i + 1; j < images.length; j++) {
                String tags1[] = images[i].tags;
                String tags2[] = images[j].tags;
                ArrayList<String> tagsIntersection = getIntersectionTags(tags1, tags2);

                /*for (String tag: tags1) {
                    System.out.print(String.format("%s, ", tag));
                }

                System.out.println();

                for (String tag: tags2) {
                    System.out.print(String.format("%s, ", tag));
                }
                System.out.println();


                for (String tag: tagsIntersection) {
                    System.out.print(String.format("%s, ", tag));
                }
                System.out.println();*/

                double interestFactor = getInterestFactor(tags1.length, tags2.length, tagsIntersection.size());

                ImageList imageList = new ImageList();
                imageList.image1 = images[i];
                imageList.image2 = images[j];
                imageList.interestFactor = interestFactor;
                imageList.commonTags = tagsIntersection;
                imageListArrayList.add(imageList);
            }

            for (ImageList imageList: imageListArrayList) {
                System.out.println(String.format("Image %s Tags: %s \nImage %s Tags: %s \nCommon Tags: %s \nInterestFactor = %s\n",
                        imageList.image1.index,
                        Arrays.toString(imageList.image1.tags),
                        imageList.image2.index,
                        Arrays.toString(imageList.image2.tags),
                        imageList.commonTags,
                        imageList.interestFactor));
                //System.out.println(String.format("Image %s, Image %s => InterestFactor = %s", imageList.image1.index, imageList.image2.index, imageList.interestFactor));
            }

        }
    }

    private static ArrayList<String> getIntersectionTags(String[] tags1, String[] tags2) {
        ArrayList<String> tagsIntersection = new ArrayList<>();
        for (int i = 0; i < tags1.length; i++) {
            for (int j = 0; j < tags2.length; j++) {
                if(tags1[i].equals(tags2[j]))
                {
                    tagsIntersection.add(tags1[i]);
                }
            }
        }
        return tagsIntersection;
    }

    private static double getInterestFactor(int size1, int size2, int intersection) {
        int a = size1 - intersection;
        int b = size2 - intersection;
        int c = intersection;
        return min(a, b, c);
    }

    private static double min(double a, double b, double c) {
        return Math.min(Math.min(a, b), c);
    }
}

class Image
{
    public int index;
    public char imageMode;
    public int numberOfTags;
    public String[] tags;
}

class ImageList {
    public Image image1;
    public Image image2;
    public ArrayList<String> commonTags;
    public double interestFactor;
}