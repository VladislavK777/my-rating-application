export default function ({ redirect }) {
  if (localStorage.getItem('role') !== 'PARTNER') {
    if (localStorage.getItem('token')) {
      return redirect('/dashboard/partners')
    }
    return redirect('/dashboard/account/auth')
  }
}
