import { useForm, useFieldArray } from 'react-hook-form';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { useNavigate, useParams, Link } from 'react-router-dom';
import { useEffect } from 'react';

type ExpenseSplit = {
  userId: string;
  amountOwed: number;
};

type ExpenseFormValues = {
  description: string;
  totalAmount: number;
  splitType: string;
  date: string;
  expenseSplits: ExpenseSplit[];
};

export default function ExpenseFormPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { register, handleSubmit, control, setValue, reset } = useForm<ExpenseFormValues>({
    defaultValues: {
      description: '',
      totalAmount: 0,
      splitType: 'equal',
      date: new Date().toISOString().slice(0, 10),
      expenseSplits: [{ userId: '', amountOwed: 0 }],
    },
  });
  const { fields, append, remove } = useFieldArray({
    control,
    name: 'expenseSplits',
  });

  // If editing, fetch and populate
  useEffect(() => {
    if (id) {
      fetch(`/api/expenses/${id}`, { credentials: 'include' })
        .then(res => res.json())
        .then(data => {
          reset({
            description: data.description,
            totalAmount: data.totalAmount,
            splitType: data.splitType,
            date: data.date.slice(0, 10),
            expenseSplits: data.expenseSplits.map((s: any) => ({
              userId: s.userId,
              amountOwed: s.amountOwed,
            })),
          });
        });
    }
  }, [id, reset]);

  const onSubmit = async (values: ExpenseFormValues) => {
    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/expenses/${id}` : '/api/expenses';
    await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(values),
    });
    navigate('/expenses');
  };

  return (
    <div className="max-w-2xl mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle className="text-xl">{id ? 'Edit Expense' : 'Add Expense'}</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
            <div>
              <Label>Description</Label>
              <Input {...register('description', { required: true })} />
            </div>
            <div>
              <Label>Total Amount</Label>
              <Input type="number" step="0.01" {...register('totalAmount', { required: true })} />
            </div>
            <div>
              <Label>Split Type</Label>
              <Input {...register('splitType', { required: true })} />
            </div>
            <div>
              <Label>Date</Label>
              <Input type="date" {...register('date', { required: true })} />
            </div>
            <div>
              <Label>Splits</Label>
              <div className="space-y-2">
                {fields.map((field, idx) => (
                  <div key={field.id} className="flex gap-2 items-center">
                    <Input placeholder="User ID" {...register(`expenseSplits.${idx}.userId` as const, { required: true })} />
                    <Input type="number" step="0.01" placeholder="Amount" {...register(`expenseSplits.${idx}.amountOwed` as const, { required: true })} />
                    <Button type="button" variant="destructive" size="sm" onClick={() => remove(idx)}>Remove</Button>
                  </div>
                ))}
                <Button type="button" variant="outline" size="sm" onClick={() => append({ userId: '', amountOwed: 0 })}>
                  Add Split
                </Button>
              </div>
            </div>
            <div className="flex justify-between">
              <Button asChild variant="outline">
                <Link to="/expenses">Cancel</Link>
              </Button>
              <Button type="submit">{id ? 'Update' : 'Create'}</Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}