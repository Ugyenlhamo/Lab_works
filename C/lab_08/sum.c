#include<stdio.h>
#include<stdlib.h>

int main()
{
	int *ptr;	//declaration of integer pointer
	int n;	//to store array limit
	int i;		//loop counter
	int sum;	//to store sum of all elements

	printf("Enter limit of the array: ");
	scanf("%d",&n);

	//declare memory dynamically
	ptr=(int*)malloc(n*sizeof(int));

	//read array elements
	for(i=0;i<n;i++)
	{
		printf("Enter element %d: ",i+1);
		scanf("%d",(ptr+i));
	}

	//print array elements
	printf("\nEntered  elements are:\n");
	for(i=0;i<n;i++)
	{
		printf("%d\n",*(ptr+i));
	}
	
	//calculate sum of all elements
	sum=0;	//assign 0 to replace garbage value
	for(i=0;i<n;i++)
	{
		sum+=*(ptr+i);
	}
	printf("Sum of array elements is: %d\n",sum);
	
	//free memory
	free(ptr);	//hey, don't forget to free dynamically allocated memory.
	
	return 0;
}