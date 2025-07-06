import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';

type ExpenseSplit = {
  id: string;
  expense_id: string;
  userId: string;
  amountOwed: number;
};

type Expense = {
  id: string;
  description: string;
  totalAmount: number;
  createdByUserId: string;
  splitType: string;
  date: string;
  expenseSplits: ExpenseSplit[];
};

export default function ExpenseDetailPage() {
  const { id } = useParams();
  const [expense, setExpense] = useState<Expense | null>(null);

  useEffect(() => {
    fetch(`/api/expenses/${id}`, { credentials: 'include' })
      .then(res => res.json())
      .then(setExpense);
  }, [id]);

  if (!expense) {
    return (
      <div className="max-w-2xl mx-auto py-8">
        <Card>
          <CardContent>
            <div className="text-center py-8 text-muted-foreground">Loading...</div>
          </CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle className="text-xl">{expense.description}</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="mb-4 flex justify-between items-center">
            <div>
              <div className="text-muted-foreground text-sm">{expense.splitType}</div>
              <div className="text-xs text-muted-foreground">{new Date(expense.date).toLocaleDateString()}</div>
            </div>
            <div className="text-2xl font-bold text-primary">₹{expense.totalAmount}</div>
          </div>
          <div className="mb-2 font-semibold">Splits</div>
          <div className="rounded border bg-muted p-3 space-y-2">
            {expense.expenseSplits.map(split => (
              <div key={split.id} className="flex items-center justify-between py-1">
                <div className="flex items-center gap-2">
                  <Avatar className="h-6 w-6">
                    <AvatarFallback>{split.userId.slice(0, 2).toUpperCase()}</AvatarFallback>
                  </Avatar>
                  <span className="text-sm text-muted-foreground">User: {split.userId}</span>
                </div>
                <span className="font-medium">₹{split.amountOwed}</span>
              </div>
            ))}
          </div>
          <div className="mt-6 flex justify-between">
            <Button asChild variant="outline">
              <Link to="/expenses">Back to Expenses</Link>
            </Button>
            <Button variant="destructive">Delete</Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}