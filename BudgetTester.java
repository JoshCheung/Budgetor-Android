public class BudgetTester
{
    public static void main(String[] args)
    {
        BudgetList A = new BudgetList();
       	BudgetList B = new BudgetList();
      
        for(int i=1; i<=10; i++)
        {
            A.add(i);
        }
        for(A.moveFront(); A.index()!= -1; A.moveNext())
        {
        	System.out.println((A.index() + 1) + ": $" + A.get());
        }
        A.changeEntry(5, 15.00);
        System.out.println(A);
    }
}