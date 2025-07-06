import { store } from "@/store";
import {expenseApi} from "./api";

export async function getExpenses() {
  const userId = store.getState().auth.user.id;
  const res = await expenseApi.get(`user/${userId}`);
  console.log('state', store.getState());
  console.log('user id:', userId);
  console.log('result:', res);

  if (res.status !== 200) throw new Error('Failed to fetch expenses');
  return res.data;
}

export async function getExpense(id: string) {
  const res = await fetch(`expenses/${id}`, { credentials: 'include' });
  if (!res.ok) throw new Error('Failed to fetch expense');
  return res.json();
}

export async function createExpense(data: any) {
  const res = await fetch('expenses', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to create expense');
  return res.json();
}

export async function updateExpense(id: string, data: any) {
  const res = await fetch(`expenses/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to update expense');
  return res.json();
}

export async function deleteExpense(id: string) {
  const res = await fetch(`expenses/${id}`, {
    method: 'DELETE',
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Failed to delete expense');
  return res.json();
}