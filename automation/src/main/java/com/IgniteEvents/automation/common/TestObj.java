package com.IgniteEvents.automation.common;

public class TestObj {
	int a , b;
	public TestObj(int a, int b) {
				this.a= a;
				this.b = b;
					
	}
	
	
		void calc (TestObj o){
			o.a +=10;
			o.b +=10;
			
		}
		
			void display(){
				innerB iner = new innerB();
				iner.display1();
				
			}
		
		static class innerB{
			
		
			public static void main(String[] args) {
				TestObj test = new TestObj(12, 23);
				test.display();
				System.out.println(test.a +  test.b);
				test.calc(test);
				System.out.println(test.a);
				
				}
			void display1(){
				System.out.println("Display");
			}
				
			}
		
	
			
		}


