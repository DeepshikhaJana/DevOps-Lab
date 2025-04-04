package com.example;

import java.io.*;
import java.util.*;

public class StudentGrades {
    private static final String GRADES_FILE = "student_grades.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static class Grade {
        private int studentId;
        private String subject;
        private double score;
        private String grade;

        public Grade(int studentId, String subject, double score) {
            this.studentId = studentId;
            this.subject = subject;
            this.score = score;
            this.grade = calculateGrade(score);
        }

        private String calculateGrade(double score) {
            if (score >= 90) return "A";
            else if (score >= 80) return "B";
            else if (score >= 70) return "C";
            else if (score >= 60) return "D";
            else return "F";
        }

        @Override
        public String toString() {
            return studentId + ", " + subject + ", " + score + ", " + grade;
        }
    }

    public static void addGrade() {
        try (FileWriter fw = new FileWriter(GRADES_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            System.out.print("Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter Subject: ");
            String subject = scanner.nextLine();
            
            System.out.print("Enter Score (0-100): ");
            double score = scanner.nextDouble();
            scanner.nextLine();
            
            if (score < 0 || score > 100) {
                System.out.println("Invalid score. Score must be between 0 and 100.");
                return;
            }
            
            Grade grade = new Grade(studentId, subject, score);
            out.println(grade);
            System.out.println("Grade added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static void displayStudentGrades() {
        System.out.print("Enter Student ID to view grades: ");
        int searchId = scanner.nextInt();
        scanner.nextLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader(GRADES_FILE))) {
            String line;
            boolean found = false;
            System.out.println("\nGrades for Student ID " + searchId + ":");
            System.out.println("Subject\tScore\tGrade");
            System.out.println("------------------------");
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (Integer.parseInt(data[0]) == searchId) {
                    found = true;
                    System.out.printf("%s\t%.1f\t%s%n", data[1], Double.parseDouble(data[2]), data[3]);
                }
            }
            
            if (!found) {
                System.out.println("No grades found for this student.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static void calculateGPA() {
        System.out.print("Enter Student ID to calculate GPA: ");
        int searchId = scanner.nextInt();
        scanner.nextLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader(GRADES_FILE))) {
            String line;
            double totalScore = 0;
            int subjectCount = 0;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (Integer.parseInt(data[0]) == searchId) {
                    totalScore += Double.parseDouble(data[2]);
                    subjectCount++;
                }
            }
            
            if (subjectCount > 0) {
                double gpa = totalScore / subjectCount;
                System.out.printf("GPA for Student ID %d: %.2f%n", searchId, gpa);
            } else {
                System.out.println("No grades found for this student.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
} 