import java.util.*;

class Coordinate {
	private double x1;
	private double x2;
	private double x3;
	private int importance;
	private char group;

	public Coordinate(double a, double b, int c) {
		this.x1 = a;
		this.x2 = b;
		this.importance = c;
	}

	public Coordinate(double a, double b, double c, int d) {
		this.x1 = a;
		this.x2 = b;
		this.x3 = c;
		this.importance = d;
		this.group = 0;
	}

	public double getX1() { return this.x1; }
	public double getX2() { return this.x2; }
	public double getX3() { return this.x3; }
	public int getImportance() { return this.importance; }
	public void setImportance(int imp) { this.importance = imp; }
	public char getGroup() { return this.group; }
	public void setGroup(char g) { this.group = g; }

	public String toString() {
		return "x = " + getX1() + ", y = " + getX2() + ", z = " + getX3() + ", imp = " + getImportance();
	}
}

class coordSortByX1 implements Comparator<Coordinate> {
	@Override
	public int compare(Coordinate a, Coordinate b) {
		int comparison = Double.compare(a.getX1(), b.getX1());
		if (comparison == 0) {
			comparison = Double.compare(a.getX2(), b.getX2());
		}
		if (comparison == 0) {
			comparison = Double.compare(a.getX3(), b.getX3());
		}
		return comparison;
	}
}

class coordSortByX2 implements Comparator<Coordinate> {
	@Override
	public int compare(Coordinate a, Coordinate b) {
		int comparison = Double.compare(a.getX2(), b.getX2());
		if (comparison == 0) {
			comparison = Double.compare(a.getX3(), b.getX3());
		}
		if (comparison == 0) {
			comparison = Double.compare(a.getX1(), b.getX1());
		}
		return comparison;
	}
}

class coordSortByX3 implements Comparator<Coordinate> {
	@Override
	public int compare(Coordinate a, Coordinate b) {
		int comparison = Double.compare(a.getX3(), b.getX3());
		if (comparison == 0) {
			comparison = Double.compare(a.getX1(), b.getX1());
		}
		if (comparison == 0) {
			comparison = Double.compare(a.getX2(), b.getX2());
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
			} else if (sortBy == 'Z') {
				double aX3 = a.getX3();
				double bX3 = b.getX3();
				compare = Double.compare(aX3, bX3);
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
				} else if (sortBy == 'Y' || sortBy == 'Z') {
					double aX1 = a.getX1();
					double bX1 = b.getX1();
					compare = Double.compare(aX1, bX1);
				}

				if (compare < 0) {
					mergeSorted.add(a);
					aCounter++;
				} else {
					mergeSorted.add(b);
					bCounter++;
				}
			}
		}

		return mergeSorted;
	}

	private static List<Coordinate> mergeAll(List<Coordinate> list) {
		list = mergeSort(list, 'X');
		return mergeAllRecursive(list);
	}

	private static List<Coordinate> mergeAllRecursive(List<Coordinate> pointList) {

		int size = pointList.size();
		if (size <= 1) {
			return pointList;
		}

		double mid = pointList.get(size / 2).getX1();
		List<Coordinate> leftSide = new ArrayList<>();
		List<Coordinate> rightSide = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			Coordinate c = pointList.get(i);
			double x1 = c.getX1();
			if (x1 < mid) {
				leftSide.add(c);
			} else {
				rightSide.add(c);
			}
		}

		List<Coordinate> p = mergeAllRecursive(leftSide);
		List<Coordinate> q = mergeAllRecursive(rightSide);

		return mergeAllHelper(p, q);
	}

	private static List<Coordinate> mergeAllHelper(List<Coordinate> p, List<Coordinate> q) {

		List<Coordinate> pqCombine = new ArrayList<>();
		int pSize = p.size();
		int qSize = q.size();

		for (int i = 0; i < pSize; i++) {
			Coordinate a = p.get(i);
			a.setGroup('P');
			pqCombine.add(a);
		}

		for (int i = 0; i < qSize; i++) {
			Coordinate b = q.get(i);
			b.setGroup('Q');
			pqCombine.add(b);
		}

		pqCombine = mergeSort(pqCombine, 'Y');
		return merge2D(pqCombine);
	}

	private static List<Coordinate> merge2D(List<Coordinate> pointList) {

		int size = pointList.size();
		if (size <= 1) {
			return pointList;
		}

		double mid = pointList.get(size / 2).getX2();
		List<Coordinate> leftSide = new ArrayList<>();
		List<Coordinate> rightSide = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			Coordinate c = pointList.get(i);
			double x2 = c.getX2();
			if (x2 < mid) {
				leftSide.add(c);
			} else {
				rightSide.add(c);
			}
		}

		List<Coordinate> p = merge2D(leftSide);
		List<Coordinate> q = merge2D(rightSide);

		return mergeHelper2D(p, q);
	}

	private static List<Coordinate> mergeHelper2D(List<Coordinate> pPoints, List<Coordinate> qPoints) {
		int pSize = pPoints.size();
		int qSize = qPoints.size();
		int totalCount = pSize + qSize;

		List<Coordinate> mergedPQ = new ArrayList<Coordinate>();
		int pCounter = 0;
		int qCounter = 0;
		int impCounter = 0;

		for (int i = 0; i < totalCount; i++) {
			if (pCounter >= pSize) {
				while (qCounter < qSize) {
					Coordinate q = qPoints.get(qCounter);
					if (q.getGroup() == 'Q') {
						q.setImportance(q.getImportance() + impCounter);
					}
					mergedPQ.add(q);
					qCounter++;
				}
				break;
			}

			if (qCounter >= qSize) {
				while (pCounter < pSize) {
					mergedPQ.add(pPoints.get(pCounter));
					pCounter++;
				}
				break;
			}

			Coordinate p = pPoints.get(pCounter);
			Coordinate q = qPoints.get(qCounter);

			int comparePQ = Double.compare(p.getX3(), q.getX3());
			if (comparePQ > 0) {
				if (q.getGroup() == 'Q') {
					q.setImportance(q.getImportance() + impCounter);
				}
				mergedPQ.add(q);
				qCounter++;
			} else {
				mergedPQ.add(p);
				pCounter++;
				if (p.getGroup() == 'P') {
					impCounter++;
				}
			}
		}

		return mergedPQ;
	}

	private static void naiveSolve(List<Coordinate> list, int totalCount) {

		for (int i = 0; i < totalCount; i++) {
			Coordinate a = list.get(i);
			double aX1 = a.getX1();
			double aX2 = a.getX2();
			double aX3 = a.getX3();
			int imp = 0;
			for (int j = 0; j < totalCount; j++) {
				if (i == j) {
					continue;
				}
				Coordinate b = list.get(j);
				double bX1 = b.getX1();
				double bX2 = b.getX2();
				double bX3 = b.getX3();
				if (aX1 >= bX1 && aX2 >= bX2 && aX3 >= bX3) {
					imp++;
				}
			}

			a.setImportance(imp);
		}
	}

	private static void printSolution(List<Coordinate> pointList, int totalCount) {
		pointList = mergeSort(pointList, 'X');
		for (int i = 0; i < totalCount; i++) {
			System.out.println(pointList.get(i).getImportance());
		}
	}

	private static void checkSolution(List<Coordinate> pointList, int totalCount) {
		for (int i = 0; i < totalCount; i++) {
			Coordinate a = pointList.get(i);
			double aX1 = a.getX1();
			double aX2 = a.getX2();
			double aX3 = a.getX3();
			int imp = 0;
			for (int j = 0; j < totalCount; j++) {
				if (i == j) {
					continue;
				}
				Coordinate b = pointList.get(j);
				double bX1 = b.getX1();
				double bX2 = b.getX2();
				double bX3 = b.getX3();
				if (aX1 >= bX1 && aX2 >= bX2 && aX3 >= bX3) {
					imp++;
				}
			}

			if (a.getImportance() != imp) {
				System.out.println("\nerror: wrong solution");
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
				if (input.length != 3) {
					throw new NumberFormatException();
				}
				double x = Double.parseDouble(input[0]);
				double y = Double.parseDouble(input[1]);
				double z = Double.parseDouble(input[2]);

				pointList.add(new Coordinate(x, y, z, 0));
			}
		} catch (NumberFormatException | NoSuchElementException e) {
			System.err.println("error: faulty input");
			keyboard.close();
			System.exit(1);
		}

		keyboard.close();

		return pointList;
	}

	public static void main(String[] args) {

		List<Coordinate> pointList = readInput();
		int totalCount = pointList.size();
		
		// final long startTime = System.nanoTime();
		pointList = mergeAll(pointList);
		// naiveSolve(pointList, totalCount);
		// final long duration = System.nanoTime() - startTime;

		printSolution(pointList, totalCount);
		// checkSolution(pointList, totalCount);

		// System.out.println("\nduration = " + duration);
	}
}
