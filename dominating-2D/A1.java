import java.util.*;

class Coordinate {
	private double x1;
	private double x2;
	private int importance;

	public Coordinate(double a, double b, int c) {
		this.x1 = a;
		this.x2 = b;
		this.importance = c;
	}

	public double getX1() { return this.x1; }
	public double getX2() { return this.x2; }
	public int getImportance() { return this.importance; }
	public void setImportance (int imp) { this.importance = imp; }
	public String toString() {
		return "x = " + getX1() + ", y = " + getX2() + ", imp = " + getImportance();
	}
}

class coordSortByX1 implements Comparator<Coordinate> {
	@Override
	public int compare(Coordinate a, Coordinate b) {
		int comparison = Double.compare(a.getX1(), b.getX1());
		if (comparison == 0) {
			comparison = Double.compare(a.getX2(), b.getX2());
		}
		return comparison;
	}
}

class coordSortByX2 implements Comparator<Coordinate> {
	@Override
	public int compare(Coordinate a, Coordinate b) {
		int comparison = Double.compare(a.getX2(), b.getX2());
		if (comparison == 0) {
			comparison = Double.compare(a.getX1(), b.getX1());
		}
		return comparison;
	}
}

public class A1 {

	private static List<Coordinate> mergeSort(List<Coordinate> list, char sortBy) {
		int size = list.size();

		if (size <= 1) {
			return list;
		}

		int mid = size / 2;
		List<Coordinate> leftSide = new ArrayList<>();
		List<Coordinate> rightSide = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (i < mid) {
				leftSide.add(list.get(i));
			} else {
				rightSide.add(list.get(i));
			}
		}

		List<Coordinate> leftSorted = mergeSort(leftSide, sortBy);
		List<Coordinate> rightSorted = mergeSort(rightSide, sortBy);

		return mergeSortHelper(leftSorted, rightSorted, sortBy);
	}

	private static List<Coordinate> mergeSortHelper(List<Coordinate> listA, List<Coordinate> listB, char sortBy) {
		int aSize = listA.size();
		int bSize = listB.size();
		int totalSize = aSize + bSize;

		List<Coordinate> mergeSorted = new ArrayList<>();

		int aCounter = 0;
		int bCounter = 0;

		for (int i = 0; i < totalSize; i++) {
			if (aCounter == aSize) {
				while (bCounter < bSize) {
					mergeSorted.add(listB.get(bCounter));
					bCounter++;
				}
				break;
			}
			
			if (bCounter == bSize) {
				while (aCounter < aSize) {
					mergeSorted.add(listA.get(aCounter));
					aCounter++;
				}
				break;
			}
			Coordinate a = listA.get(aCounter);
			Coordinate b = listB.get(bCounter);

			int compare = 0;
			
			if (sortBy == 'X') {
				double aX1 = a.getX1();
				double bX1 = b.getX1();
				compare = Double.compare(aX1, bX1);
			} else if (sortBy == 'Y') {
				double aX2 = a.getX2();
				double bX2 = b.getX2();
				compare = Double.compare(aX2, bX2);
			}

			if (compare < 0) {
				mergeSorted.add(a);
				aCounter++;
			} else if (compare > 0) {
				mergeSorted.add(b);
				bCounter++;
			} else {
				
				if (sortBy == 'X') {
					double aX2 = a.getX2();
					double bX2 = b.getX2();
					compare = Double.compare(aX2, bX2);
				} else if (sortBy == 'Y') {
					double aX1 = a.getX1();
					double bX1 = b.getX1();
					compare = Double.compare(aX1, bX1);
				}
				
				if (compare < 0) {
					mergeSorted.add(a);
					aCounter++;
				} else if (compare > 0) {
					mergeSorted.add(b);
					bCounter++;
				} else {
					mergeSorted.add(a);
					mergeSorted.add(b);
					aCounter++;
					bCounter++;
					i++;
				}
			}
		}

		return mergeSorted;
	}
	/*
	// presorts on x1 and divides x1 sorted set
	private static List<Coordinate> mergeImportanceVer1(List<Coordinate> list) {
		List<Coordinate> sortedList = mergeSort(list, 'X');
		return mergeImpRecursiveVer1(sortedList, 0, sortedList.size());
	}
	
	private static List<Coordinate> mergeImpRecursiveVer1(List<Coordinate> pointList, int start, int end) {
		
		int size = end - start;
		if (size <= 1) {
			return pointList;
		}
		
		int mid = start + ((end - start) / 2);
		List<Coordinate> leftSide = new ArrayList<>();
		List<Coordinate> rightSide = new ArrayList<>();
		
		for (int i = start; i < end; i++) {
			if (i < mid) {
				leftSide.add(pointList.get(i));
			} else {
				rightSide.add(pointList.get(i));
			}
		}
		
		List<Coordinate> p = mergeImpRecursiveVer1(leftSide, 0, leftSide.size());
		List<Coordinate> q = mergeImpRecursiveVer1(rightSide, 0, rightSide.size());
		
		List<Coordinate> mergedPQ = mergeImportanceHelper(p, q);
		return mergedPQ;
	}
	*/
	
	// presorts on x2 but does not use divide and conquer to find x1 median
	private static List<Coordinate> mergeImportance(List<Coordinate> list) {
		List<Coordinate> sortedX1 = mergeSort(list, 'X');
		List<Coordinate> sortedX2 = mergeSort(list, 'Y');
		
		return mergeImpRecursive(sortedX1, sortedX2);
	}
	
	private static List<Coordinate> mergeImpRecursive(List<Coordinate> sortedX1, List<Coordinate> pointList) {

		int size = pointList.size();
		if (size <= 1) {
			return pointList;
		}

		double mid = sortedX1.get(size / 2).getX1();
		List<Coordinate> leftSide = new ArrayList<>();
		List<Coordinate> rightSide = new ArrayList<>();
		
		List<Coordinate> sortedX1Left = new ArrayList<>();
		List<Coordinate> sortedX1Right = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			Coordinate c = pointList.get(i);
			double x1 = c.getX1();
			if (x1 < mid) {
				leftSide.add(c);
			} else {
				rightSide.add(c);
			}
			
			if (i < size / 2) {
				sortedX1Left.add(sortedX1.get(i));
			} else {
				sortedX1Right.add(sortedX1.get(i));
			}
		}

		List<Coordinate> p = mergeImpRecursive(sortedX1Left, leftSide);
		List<Coordinate> q = mergeImpRecursive(sortedX1Right, rightSide);

		return mergeImportanceHelper(p, q);
	}

	private static List<Coordinate> mergeImportanceHelper(List<Coordinate> pPoints, List<Coordinate> qPoints) {

		int pSize = pPoints.size();
		int qSize = qPoints.size();
		int totalCount = pSize + qSize;

		List<Coordinate> mergedPQ = new ArrayList<Coordinate>();
		int pCounter = 0;
		int qCounter = 0;

		for (int i = 0; i < totalCount; i++) {
			if (pCounter == pSize) {
				while (qCounter < qSize) {
					Coordinate q = qPoints.get(qCounter);
					q.setImportance(q.getImportance() + pCounter);
					mergedPQ.add(q);
					qCounter++;
				}
				break;
			}

			if (qCounter == qSize) {
				while (pCounter < pSize) {
					mergedPQ.add(pPoints.get(pCounter));
					pCounter++;
				}
				break;
			}

			Coordinate p = pPoints.get(pCounter);
			Coordinate q = qPoints.get(qCounter);
			int comparePQ = Double.compare(p.getX2(), q.getX2());
			if (comparePQ > 0) {
				q.setImportance(q.getImportance() + pCounter);
				mergedPQ.add(q);
				qCounter++;
			} else if (comparePQ < 0) {
				mergedPQ.add(p);
				pCounter++;
			} else {
				q.setImportance(q.getImportance() + pCounter);
				mergedPQ.add(p);
				mergedPQ.add(q);
				pCounter++;
				qCounter++;
				i++;
			}
		}

		return mergedPQ;
	}
	
	private static void naiveSolve(List<Coordinate> pointList) {
		
		int totalCount = pointList.size();
		for (int i = 0; i < totalCount; i++) {
			Coordinate a = pointList.get(i);
			double aX1 = a.getX1();
			double aX2 = a.getX2();
			int imp = 0;
			for (int j = 0; j < totalCount; j++) {
				if (i == j) {
					continue;
				}
				Coordinate b = pointList.get(j);
				double bX1 = b.getX1();
				double bX2 = b.getX2();
				if (aX1 >= bX1 && aX2 >= bX2) {
					imp++;
				}
			}

			a.setImportance(imp);
		}
		
	}
	
	private static void checkSolution(List<Coordinate> pointList) {
		
		int totalCount = pointList.size();
		for (int i = 0; i < totalCount; i++) {
			Coordinate a = pointList.get(i);
			double aX1 = a.getX1();
			double aX2 = a.getX2();
			int imp = 0;
			for (int j = 0; j < totalCount; j++) {
				if (i == j) {
					continue;
				}
				Coordinate b = pointList.get(j);
				double bX1 = b.getX1();
				double bX2 = b.getX2();
				if (aX1 >= bX1 && aX2 >= bX2) {
					imp++;
				}
			}

			if (a.getImportance() != imp) {
				System.out.println("error: incorrect importance (expected " + imp + ", but was" + a.getImportance() + ")");
				break;
			}
			
			if (i == totalCount - 1) {
				System.out.println("\ncorrect");
			}
		}
	}
	
	private static List<Coordinate> readInput() {
		
		List<Coordinate> pointList = new ArrayList<>();
		
		Scanner keyboard = new Scanner(System.in);
		try {
			int totalCount = Integer.parseInt(keyboard.nextLine());

			String[] input;
			for (int i = 0; i < totalCount; i++) {
				input = keyboard.nextLine().split(" ");
				double x = Double.parseDouble(input[0]);
				double y = Double.parseDouble(input[1]);

				pointList.add(new Coordinate(x, y, 0));
			}
		} catch (NumberFormatException|NoSuchElementException e) {
			System.err.println("error: faulty input");
			keyboard.close();
			return new ArrayList<Coordinate>();
		}

		keyboard.close();
		
		return pointList;
	}
	
	private static void printImportanceList(List<Coordinate> list) {
		int totalCount = list.size();
		List<Coordinate> sortedList = mergeSort(list, 'X');
		for (int i = 0; i < totalCount; i++) {
			System.out.println(sortedList.get(i).getImportance());
		}
	}

	public static void main(String[] args) {

		List<Coordinate> pointList = readInput();
		if (pointList.isEmpty()) {
			return;
		}
		
		// final long startTime = System.nanoTime();
		pointList = mergeImportance(pointList);  // presorts on x2 but doesn't use d&c to find x1 median
		// pointList = mergeImportanceVer1(pointList); // presorts on x1
		// naiveSolve(pointList);
		// final long duration = System.nanoTime() - startTime;

		printImportanceList(pointList);
		// checkSolution(pointList);

		// System.out.println("\nduration = " + duration);
	}
}
