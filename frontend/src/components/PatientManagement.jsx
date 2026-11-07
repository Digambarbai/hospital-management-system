import { useMemo, useState } from 'react'
import Icon from './Icon.jsx'
import './PatientManagement.css'

const seedPatients = [
  { id: 'PT-1001', name: 'Emily Johnson', gender: 'Female', dob: '1988-04-12', mobile: '+1 555 0182', email: 'emily.j@example.com', blood: 'O+', address: '42 Oak Street, Boston', emergency: 'Michael Johnson · +1 555 0143', registered: '24 Oct 2025', status: 'Active' },
  { id: 'PT-1002', name: 'Robert Williams', gender: 'Male', dob: '1975-09-03', mobile: '+1 555 0187', email: 'robert.w@example.com', blood: 'A+', address: '18 Pine Avenue, Boston', emergency: 'Lily Williams · +1 555 0121', registered: '23 Oct 2025', status: 'Active' },
  { id: 'PT-1003', name: 'Sophia Martinez', gender: 'Female', dob: '1995-01-20', mobile: '+1 555 0115', email: 'sophia.m@example.com', blood: 'B+', address: '76 Lake Road, Boston', emergency: 'Daniel Martinez · +1 555 0196', registered: '22 Oct 2025', status: 'Follow-up' },
  { id: 'PT-1004', name: 'David Brown', gender: 'Male', dob: '1969-11-08', mobile: '+1 555 0165', email: 'david.b@example.com', blood: 'AB+', address: '9 Cedar Lane, Boston', emergency: 'Sarah Brown · +1 555 0108', registered: '21 Oct 2025', status: 'Active' },
  { id: 'PT-1005', name: 'Olivia Wilson', gender: 'Female', dob: '2001-06-17', mobile: '+1 555 0133', email: 'olivia.w@example.com', blood: 'O-', address: '111 River Walk, Boston', emergency: 'Andrew Wilson · +1 555 0172', registered: '20 Oct 2025', status: 'Inactive' },
  { id: 'PT-1006', name: 'James Anderson', gender: 'Male', dob: '1982-02-25', mobile: '+1 555 0179', email: 'james.a@example.com', blood: 'A-', address: '25 Birch Street, Boston', emergency: 'Nina Anderson · +1 555 0156', registered: '19 Oct 2025', status: 'Active' },
]

const blankPatient = { name: '', dob: '', gender: '', mobile: '', email: '', blood: '', address: '', emergency: '', status: 'Active' }
const rowsPerPage = 5

const ageFromDob = (dob) => {
  if (!dob) return '—'
  const born = new Date(dob)
  return new Date().getFullYear() - born.getFullYear() - (new Date() < new Date(new Date().getFullYear(), born.getMonth(), born.getDate()) ? 1 : 0)
}

function PatientForm({ patient, onCancel, onSave }) {
  const [form, setForm] = useState(patient ? { ...patient } : blankPatient)
  const [errors, setErrors] = useState({})
  const setValue = (event) => { const { name, value } = event.target; setForm((current) => ({ ...current, [name]: value })); setErrors((current) => ({ ...current, [name]: '' })) }
  const submit = (event) => {
    event.preventDefault()
    const next = {}
    ;['name', 'dob', 'gender', 'mobile'].forEach((field) => { if (!form[field]?.trim()) next[field] = 'This field is required.' })
    if (form.email && !/^\S+@\S+\.\S+$/.test(form.email)) next.email = 'Enter a valid email address.'
    setErrors(next)
    if (!Object.keys(next).length) onSave(form)
  }
  const field = (name, label, type = 'text', options) => <div className="patient-form-field"><label htmlFor={`patient-${name}`}>{label}{['name','dob','gender','mobile'].includes(name) && <b> *</b>}</label>{options ? <select id={`patient-${name}`} name={name} value={form[name]} onChange={setValue}><option value="">Select {label}</option>{options.map((option) => <option key={option}>{option}</option>)}</select> : <input id={`patient-${name}`} name={name} type={type} value={form[name]} onChange={setValue} />}{errors[name] && <small>{errors[name]}</small>}</div>
  return <form className="patient-form" noValidate onSubmit={submit}><div className="patient-form-grid">{field('name','Full Name')}{field('dob','Date of Birth','date')}{field('gender','Gender','text',['Female','Male','Other'])}{field('mobile','Mobile Number','tel')}{field('email','Email Address','email')}{field('blood','Blood Group','text',['A+','A-','B+','B-','AB+','AB-','O+','O-'])}<div className="patient-form-field full-width"><label htmlFor="patient-address">Address</label><textarea id="patient-address" name="address" value={form.address} onChange={setValue} rows="2" /></div><div className="patient-form-field full-width"><label htmlFor="patient-emergency">Emergency Contact</label><input id="patient-emergency" name="emergency" value={form.emergency} onChange={setValue} placeholder="Name · mobile number" /></div></div><div className="patient-modal-actions"><button type="button" className="patient-secondary-button" onClick={onCancel}>Cancel</button><button type="submit" className="patient-primary-button">{patient ? 'Save Changes' : 'Add Patient'}</button></div></form>
}

function Modal({ title, children, onClose }) { return <div className="patient-modal-overlay" role="presentation"><div className="patient-modal" role="dialog" aria-modal="true" aria-label={title}><div className="patient-modal-header"><h2>{title}</h2><button type="button" onClick={onClose} aria-label="Close dialog">×</button></div>{children}</div></div> }

function PatientManagement() {
  const [patients, setPatients] = useState(seedPatients)
  const [query, setQuery] = useState('')
  const [status, setStatus] = useState('')
  const [blood, setBlood] = useState('')
  const [page, setPage] = useState(1)
  const [modal, setModal] = useState(null)
  const [notice, setNotice] = useState('')
  const filtered = useMemo(() => patients.filter((patient) => (`${patient.id} ${patient.name} ${patient.mobile}`).toLowerCase().includes(query.toLowerCase()) && (!status || patient.status === status) && (!blood || patient.blood === blood)), [patients, query, status, blood])
  const totalPages = Math.max(1, Math.ceil(filtered.length / rowsPerPage))
  const visible = filtered.slice((page - 1) * rowsPerPage, page * rowsPerPage)
  const clearFilters = () => { setQuery(''); setStatus(''); setBlood(''); setPage(1) }
  const savePatient = (data) => {
    if (modal?.type === 'edit') { setPatients((current) => current.map((patient) => patient.id === modal.patient.id ? { ...patient, ...data } : patient)); setNotice('Patient details updated successfully.') } else { const id = `PT-${1000 + patients.length + 1}`; setPatients((current) => [{ ...data, id, registered: 'Today' }, ...current]); setNotice('New patient added successfully.') }
    setModal(null)
  }
  const deletePatient = () => { setPatients((current) => current.filter((patient) => patient.id !== modal.patient.id)); setNotice(`${modal.patient.name} was removed from the demo list.`); setModal(null); setPage(1) }
  return <section className="patient-management"><div className="patient-page-heading"><div><p className="demo-label">PATIENT DIRECTORY · DEMO DATA</p><h2>Patient Management</h2><span>Manage patient records and contact details in one place.</span></div><button className="patient-primary-button" type="button" onClick={() => setModal({ type: 'add' })}><span>+</span> Add New Patient</button></div>{notice && <div className="patient-notice" role="status"><span>✓</span>{notice}<button type="button" onClick={() => setNotice('')}>×</button></div>}<article className="patient-list-card"><div className="patient-toolbar"><div className="patient-search"><span>⌕</span><input value={query} onChange={(event) => { setQuery(event.target.value); setPage(1) }} placeholder="Search by name, ID or mobile..." /></div><select value={status} onChange={(event) => { setStatus(event.target.value); setPage(1) }} aria-label="Filter by status"><option value="">All statuses</option><option>Active</option><option>Follow-up</option><option>Inactive</option></select><select value={blood} onChange={(event) => { setBlood(event.target.value); setPage(1) }} aria-label="Filter by blood group"><option value="">All blood groups</option>{['A+','A-','B+','B-','AB+','AB-','O+','O-'].map((group) => <option key={group}>{group}</option>)}</select><button className="patient-clear-button" type="button" onClick={clearFilters}>Clear filters</button></div><div className="patient-table-wrap"><table><thead><tr><th>Patient</th><th>Gender / Age</th><th>Mobile</th><th>Blood</th><th>Registered</th><th>Status</th><th aria-label="Actions" /></tr></thead><tbody>{visible.map((patient) => <tr key={patient.id}><td><div className="patient-name-cell"><span>{patient.name.split(' ').map((part) => part[0]).join('').slice(0,2)}</span><div><strong>{patient.name}</strong><small>{patient.id}</small></div></div></td><td>{patient.gender}<small>{ageFromDob(patient.dob)} years · {patient.dob}</small></td><td>{patient.mobile}<small>{patient.email || 'No email provided'}</small></td><td><b className="blood-badge">{patient.blood || '—'}</b></td><td>{patient.registered}</td><td><span className={`patient-status ${patient.status.toLowerCase().replace('-', '')}`}>{patient.status}</span></td><td><div className="patient-actions"><button type="button" onClick={() => setModal({ type: 'view', patient })}>View</button><button type="button" onClick={() => setModal({ type: 'edit', patient })}>Edit</button><button className="delete" type="button" onClick={() => setModal({ type: 'delete', patient })}>Delete</button></div></td></tr>)}</tbody></table>{!visible.length && <div className="patient-empty"><span>⌕</span><h3>No patients found</h3><p>Try changing your search or clear the active filters.</p><button type="button" className="patient-secondary-button" onClick={clearFilters}>Clear filters</button></div>}</div><div className="patient-pagination"><span>Showing {filtered.length ? (page - 1) * rowsPerPage + 1 : 0}–{Math.min(page * rowsPerPage, filtered.length)} of {filtered.length} patients</span><div><button type="button" disabled={page === 1} onClick={() => setPage((current) => current - 1)}>Previous</button>{Array.from({ length: totalPages }, (_, index) => <button type="button" key={index + 1} className={page === index + 1 ? 'active' : ''} onClick={() => setPage(index + 1)}>{index + 1}</button>)}<button type="button" disabled={page === totalPages} onClick={() => setPage((current) => current + 1)}>Next</button></div></div></article>{modal?.type === 'add' && <Modal title="Add New Patient" onClose={() => setModal(null)}><PatientForm onCancel={() => setModal(null)} onSave={savePatient} /></Modal>}{modal?.type === 'edit' && <Modal title="Edit Patient" onClose={() => setModal(null)}><PatientForm patient={modal.patient} onCancel={() => setModal(null)} onSave={savePatient} /></Modal>}{modal?.type === 'view' && <Modal title="Patient Details" onClose={() => setModal(null)}><div className="patient-details"><div className="patient-detail-avatar">{modal.patient.name.split(' ').map((part) => part[0]).join('').slice(0,2)}</div><h3>{modal.patient.name}</h3><span>{modal.patient.id}</span><dl><div><dt>Date of birth</dt><dd>{modal.patient.dob} · {ageFromDob(modal.patient.dob)} years</dd></div><div><dt>Gender</dt><dd>{modal.patient.gender}</dd></div><div><dt>Mobile</dt><dd>{modal.patient.mobile}</dd></div><div><dt>Email</dt><dd>{modal.patient.email || '—'}</dd></div><div><dt>Blood group</dt><dd>{modal.patient.blood || '—'}</dd></div><div><dt>Address</dt><dd>{modal.patient.address || '—'}</dd></div><div><dt>Emergency contact</dt><dd>{modal.patient.emergency || '—'}</dd></div></dl><div className="patient-modal-actions"><button className="patient-primary-button" type="button" onClick={() => setModal(null)}>Close</button></div></div></Modal>}{modal?.type === 'delete' && <Modal title="Remove Patient" onClose={() => setModal(null)}><div className="patient-confirm"><span>!</span><h3>Remove {modal.patient.name}?</h3><p>This only removes the patient from the current demo session. It cannot be undone.</p><div className="patient-modal-actions"><button className="patient-secondary-button" type="button" onClick={() => setModal(null)}>Cancel</button><button className="patient-danger-button" type="button" onClick={deletePatient}>Remove Patient</button></div></div></Modal>}</section>
}

export default PatientManagement
