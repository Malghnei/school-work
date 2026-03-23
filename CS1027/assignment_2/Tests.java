
public class Tests {

	public static void main(String[] args) {
		
		// It may help to run just one test at a time until it is working correctly.
		// Debugging is usually easier on isolated test cases.
		
		//test1();
		//test2();
		test3();

	}
	
	private static void test1 () {
		System.out.print("Test 1");
		try {
			
			Train t = new Train();
			t.tryAddCar(new TrainCar(5, "lumber"));
			System.out.println(t);
			t.tryAddCar(new TrainCar(3, "lumber"));
			System.out.println(t);
			t.tryAddCar(new TrainCar(4, "lumber"));
			System.out.println(t);
			t.tryAddCar(new Reefer(4, 10, "meat"));
			System.out.println(t);
			System.out.println("lumber 9");
			System.out.println(t.tryAddCar(new TrainCar(9, "lumber")));
			System.out.println(t);
			t.tryAddCar(new TrainCar(5, "steel"));
			System.out.println(t);
			t.tryAddCar(new Reefer(6, 7, "fish"));
			System.out.println(t);
			
			System.out.println("\n" + t); // Print train
			
			String[] exp = new String[] {"<lumber, 5>", "<lumber, 3>", "<lumber, 9>", "<lumber, 4>", "<meat, 4, 10C>", "<fish, 6, 7C>"};
			
			if (checkTrain(t, exp)) {
				System.out.println("\t PASSED!");
			} else {
				System.out.println("\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void test2 () {
		System.out.print("Test 2");
		try {
			
			Train t = new Train();
			t.tryAddCar(new TrainCar(4, "medicine"));
			t.tryAddCar(new Reefer(4, 8, "meat"));
			t.tryAddCar(new TrainCar(4, "lumber"));
			t.tryAddCar(new Reefer(7, 10, "medicine"));
			t.tryAddCar(new TrainCar(7, "lumber"));
			t.tryAddCar(new TrainCar(4, "medicine"));
			t.tryAddCar(new TrainCar(4, "oil"));

			//System.out.println("\n" + t); // Print train
			
			String[] exp = new String[] {"<medicine, 4>", "<medicine, 7, 10C>", "<meat, 4, 8C>", "<medicine, 4>", "<oil, 4>", "<lumber, 4>", "<lumber, 7>"};
			
			if (checkTrain(t, exp)) {
				System.out.println("\t PASSED!");
			} else {
				System.out.println("\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void test3 () {
		System.out.print("Test 3");
		try {
			
			Train t = new Train();
			t.tryAddCar(new TrainCar(10, "computers"));
			t.tryAddCar(new TrainCar(10, "computers"));
			t.tryAddCar(new TrainCar(10, "oil"));
			t.tryAddCar(new TrainCar(8, "oil"));
			t.tryAddCar(new TrainCar(5, "oil"));
			t.tryAddCar(new TrainCar(7, "computers"));	
			t.tryAddCar(new TrainCar(8, "lumber"));
			

			t.tryRemoveCar(new TrainCar(7, "computers"));
			t.tryRemoveCar(new TrainCar(10, "oil"));
			t.tryRemoveCar(new TrainCar(8, "lumber"));
			
			t.tryAddCar(new Reefer(10, 7, "fish"));
			
			//System.out.println("\n" + t); // Print train
			
			String[] exp = new String[] {"<computers, 10>", "<computers, 10>", "<fish, 10, 7C>", "<oil, 10>", "<oil, 8>", "<oil, 5>"};
			
			if (checkTrain(t, exp)) {
				System.out.println("\t PASSED!");
			} else {
				System.out.println("\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static boolean checkTrain (Train t, String[] expCars) {
		
		int n = expCars.length;
		
		String fStr = travForward(t.getLocomotive());
		String bStr = travBackward(t.getCaboose());
		
		String[] fArr = fStr.split("%%%");
		String[] bArr = bStr.split("%%%");
		
		System.out.println("forward array:");
		for (String i: fArr) {
			System.out.println(i);
		}
		System.out.println(fArr.length);
		System.out.println("backward array:");
		for (String i: bArr) {
			System.out.println(i);
		}
		System.out.println(bArr.length);

		System.out.println(n);
		if (fArr.length != n || bArr.length != n) {System.out.println("Ran");return false;}
		
		for (int i = 0; i < n; i++) {
			// Check if trains match expCars in forward direction.
			if (!fArr[i].equals(expCars[i])) return false;
			// Check if trains match expCars in backward direction.
			if (!bArr[i].equals(expCars[n-i-1])) return false;
		}
		
		// All trains are correct in both directions.
		return true;
	}
	
	private static String travForward (DoubleNode<TrainCar> loc) {
		System.out.println("\ntrav forward:");
		String s = "";
		DoubleNode<TrainCar> curr = loc;
		while (curr != null) {
			System.out.println(curr.getElement());
			System.out.println("next: " + curr.getNext());
			s += curr.getElement().toString();
			curr = curr.getNext();
			if (curr != null) s += "%%%";
		}
		return s;
	}
	
	private static String travBackward (DoubleNode<TrainCar> cab) {
		System.out.println("\ntrav backward:");
		String s = "";
		DoubleNode<TrainCar> curr = cab;
		while (curr != null) {
			System.out.println(curr.getElement());
			System.out.println("prev: " + curr.getPrevious());
			s += curr.getElement().toString();
			curr = curr.getPrevious();
			if (curr != null) s += "%%%";
		}
		return s;
	}
}
