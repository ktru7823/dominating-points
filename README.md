# dominating-points
In 2D, each point has (x, y) coordinates. A point P1 = (x1,y1) will dominate another point P2 = (x2,y2) if x1 > x2 AND y1 > y2.  

This program uses a divide and conquer algorithm on a set of points to assign a value to each point. This value is based on the number of other points which they 'dominate'. This solution is also extended to 3D, where a point has (x,y,z) coordinates.  
<br>
![example](dominating-2D/example.PNG)

In the above example, p1 has a value of "3", since it dominates p2, p3, and p4.

Input is taken line-by-line from stdin.  
Input format (2D):  
>n  
p1(x) p1(y)  
p2(x) p2(y)  
...  
pn(x) pn(y)  

Input format (3D):  
>n  
p1(x) p1(y) p1(z)  
p2(x) p2(y) p2(z)  
...  
pn(x) pn(y) pn(z)  

Input explanation:
- Define the number of points, "n".
- List the coordinates of each point, one point per line.

Output:  
For each point, output the number of points that they dominate.  

Example of valid input (n10_0.in) and expected output (n10_0.out) are provided in the repo for both the 2D and 3D version of the problem.
