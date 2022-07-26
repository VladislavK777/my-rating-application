export default function ({ redirect }) {
  if (localStorage.getItem('role') !== 'ADMIN') {
    if (localStorage.getItem('token')) {
      return redirect('/dashboard/partner/requests')
    }
    return redirect('/dashboard/account/auth')
  }
}
