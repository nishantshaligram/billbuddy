import { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Link } from 'react-router-dom';
import { getExpenses } from '@/services/expenseService';

type Expense = {
  id: string;
  description: string;
  totalAmount: number;
  splitType: string;
  date: string;
};

export default function ExpenseListPage() {
  const [expenses, setExpenses] = useState<Expense[]>([]);

  useEffect(() => {
    try{
     const loadExpenses = async() => {
      const expenses = await getExpenses();
      setExpenses(expenses);
     }
     loadExpenses();
     console.log('expense list loading');
    }catch(e) {

    }
  }, [expenses]);

  return (
    <div className="max-w-3xl mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle className="text-xl">Expenses</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            {expenses.length === 0 && (
              <div className="text-muted-foreground text-center py-8">No expenses found.</div>
            )}
            {expenses.map(expense => (
              <Link
                to={`/expenses/${expense.id}`}
                key={expense.id}
                className="block rounded-lg border hover:shadow transition p-4 bg-white"
              >
                <div className="flex justify-between items-center">
                  <div>
                    <div className="font-semibold">{expense.description}</div>
                    <div className="text-xs text-muted-foreground">{expense.splitType} • {new Date(expense.date).toLocaleDateString()}</div>
                  </div>
                  <div className="text-lg font-bold text-primary">₹{expense.totalAmount}</div>
                </div>
              </Link>
            ))}
          </div>
          <div className="mt-6 text-right">
            <Button asChild>
              <Link to="/expenses/new">Add Expense</Link>
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}