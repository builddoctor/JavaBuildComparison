package com.builddoctor;

class HelloWorld { 
  public String helloMessage() { 
    return "Well hi there";
  }
  public static void main (String[] args) {
    HelloWorld helloWorld = new HelloWorld();
    System.out.println(helloWorld.helloMessage());
  }
}
