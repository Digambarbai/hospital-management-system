import { useState } from 'react'
import './AdminChangePasswordPage.css'
import { apiUrl } from '../api.js'

const csrfToken = () => document.cookie.split('; ').find((cookie) => cookie.startsWith('XSRF-TOKEN='))?.split('=').slice(1).join('=')

function AdminChangePasswordPage({ onNavigate }) {
  const [form, setForm] = useState({ oldPassword: '', newPassword: '', confirmPassword: '' })
  const [show, setShow] = useState({ oldPassword: false, newPassword: false, confirmPassword: false })
  const [message, setMessage] = useState(null)
  const [saving, setSaving] = useState(false)
  const change = ({ target: { name, value } }) => { setForm((current) => ({ ...current, [name]: value })); setMessage(null) }
  const submit = async (event) => {
    event.preventDefault()
    if (!form.oldPassword || !form.newPassword || !form.confirmPassword) return setMessage({ type: 'error', text: 'Complete all password fields.' })
    if (form.newPassword.length < 6) return setMessage({ type: 'error', text: 'New password must be at least 6 characters.' })
    if (form.newPassword !== form.confirmPassword) return setMessage({ type: 'error', text: 'New password and confirmation do not match.' })
    setSaving(true)
    try {
      const token = csrfToken()
      const response = await fetch(apiUrl('/api/auth/change-password'), { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json', ...(token ? { 'X-XSRF-TOKEN': token } : {}) }, body: JSON.stringify(form) })
      const body = await response.json().catch(() => ({}))
      if (!response.ok) throw new Error(body.message || 'Unable to change password.')
      setForm({ oldPassword: '', newPassword: '', confirmPassword: '' })
      setMessage({ type: 'success', text: 'Password changed successfully.' })
    } catch (error) { setMessage({ type: 'error', text: error.message }) } finally { setSaving(false) }
  }
  const field = (name, label) => <label className="account-password-field">{label}<div><input name={name} type={show[name] ? 'text' : 'password'} value={form[name]} onChange={change} autoComplete="new-password" /><button type="button" aria-label={`${show[name] ? 'Hide' : 'Show'} ${label}`} onClick={() => setShow((current) => ({ ...current, [name]: !current[name] }))}><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M2 12s3.5-6 10-6 10 6 10 6-3.5 6-10 6S2 12 2 12Z"/><circle cx="12" cy="12" r="2.5"/></svg></button></div></label>
  return <section className="admin-profile-page"><div className="admin-profile-heading"><div><p className="demo-label">ACCOUNT SETTINGS</p><h2>Change Password</h2><span>Keep your administrator account secure.</span></div><button type="button" className="admin-profile-back" onClick={() => onNavigate('Dashboard')}>Back to Dashboard</button></div>{message && <div className={`admin-profile-message ${message.type}`} role={message.type === 'error' ? 'alert' : 'status'}>{message.text}</div>}<article className="admin-profile-card change-password-page-card"><div className="change-password-page-intro"><div className="change-password-page-icon">⌕</div><div><h3>Update your password</h3><p>Enter your current password, then choose a new password of at least six characters.</p></div></div><form onSubmit={submit} noValidate>{field('oldPassword', 'Old Password')}{field('newPassword', 'New Password')}{field('confirmPassword', 'Confirm New Password')}<div className="admin-profile-actions"><button type="button" className="admin-profile-cancel" onClick={() => onNavigate('Dashboard')}>Cancel</button><button type="submit" className="admin-profile-save" disabled={saving}>{saving ? 'Saving…' : 'Save New Password'}</button></div></form></article></section>
}

export default AdminChangePasswordPage
