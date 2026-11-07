import { useEffect, useState } from 'react'
import './AdminProfilePage.css'
import { apiUrl } from '../api.js'

const csrfToken = () => document.cookie.split('; ').find((cookie) => cookie.startsWith('XSRF-TOKEN='))?.split('=').slice(1).join('=')

function AdminProfilePage({ user, onUserUpdated, onNavigate }) {
  const [form, setForm] = useState({ empId: '', name: user.name, email: user.email })
  const [profile, setProfile] = useState(null)
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)
  const [message, setMessage] = useState(null)
  const [errors, setErrors] = useState({})

  useEffect(() => {
    fetch(apiUrl('/api/auth/profile'), { credentials: 'include' })
      .then((response) => { if (!response.ok) throw new Error('Unable to load your profile.'); return response.json() })
      .then((data) => { setProfile(data); setForm({ empId: data.empId || '', name: data.name, email: data.email }) })
      .catch((error) => setMessage({ type: 'error', text: error.message }))
      .finally(() => setLoading(false))
  }, [])

  const change = ({ target: { name, value } }) => { setForm((current) => ({ ...current, [name]: value })); setErrors((current) => ({ ...current, [name]: '' })); setMessage(null) }
  const save = async (event) => {
    event.preventDefault()
    const next = {}
    if (!form.empId.trim()) next.empId = 'Employee ID is required.'
    if (!form.name.trim()) next.name = 'Name is required.'
    if (!/^\S+@\S+\.\S+$/.test(form.email)) next.email = 'Enter a valid email address.'
    setErrors(next)
    if (Object.keys(next).length) return
    setSaving(true)
    try {
      const token = csrfToken()
      const response = await fetch(apiUrl('/api/auth/profile'), { method: 'PUT', credentials: 'include', headers: { 'Content-Type': 'application/json', ...(token ? { 'X-XSRF-TOKEN': token } : {}) }, body: JSON.stringify(form) })
      const data = await response.json().catch(() => ({}))
      if (!response.ok) throw new Error(data.message || 'Unable to save profile.')
      setProfile(data)
      setForm({ empId: data.empId || '', name: data.name, email: data.email })
      onUserUpdated(data)
      setMessage({ type: 'success', text: 'Profile updated successfully.' })
    } catch (error) { setMessage({ type: 'error', text: error.message }) } finally { setSaving(false) }
  }

  return <section className="admin-profile-page"><div className="admin-profile-heading"><div><p className="demo-label">ACCOUNT SETTINGS</p><h2>My Profile</h2><span>Manage your administrator profile details.</span></div><button type="button" className="admin-profile-back" onClick={() => onNavigate('Dashboard')}>Back to Dashboard</button></div>{message && <div className={`admin-profile-message ${message.type}`} role="status">{message.text}</div>}<article className="admin-profile-card">{loading ? <div className="admin-profile-loading">Loading profile…</div> : <><div className="admin-profile-identity"><div className="admin-profile-avatar">{(form.name || 'A').split(' ').map((part) => part[0]).join('').slice(0, 2)}</div><div><h3>{profile?.name || form.name}</h3><p>Administrator account</p></div><span className="admin-role-pill">ADMIN</span></div><form onSubmit={save} noValidate><div className="admin-profile-grid"><label>Employee ID<input name="empId" value={form.empId} onChange={change} />{errors.empId && <small>{errors.empId}</small>}</label><label>Name<input name="name" value={form.name} onChange={change} />{errors.name && <small>{errors.name}</small>}</label><label>Email<input name="email" type="email" value={form.email} onChange={change} />{errors.email && <small>{errors.email}</small>}</label><label>Account Role<input value="Administrator" readOnly disabled /></label><label>Account Status<input value={profile?.active ? 'Active' : 'Inactive'} readOnly disabled /></label></div><div className="admin-profile-actions"><button type="button" className="admin-profile-cancel" onClick={() => setForm({ empId: profile?.empId || '', name: profile?.name || user.name, email: profile?.email || user.email })}>Cancel</button><button type="submit" className="admin-profile-save" disabled={saving}>{saving ? 'Saving…' : 'Save Changes'}</button></div></form></>}</article></section>
}

export default AdminProfilePage
