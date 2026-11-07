import Icon from './Icon.jsx'

function StatCard({ label, value, trend, icon, tone }) {
  return (
    <article className="stat-card">
      <div><p>{label}</p><h2>{value}</h2><span className={trend.startsWith('+') ? 'trend up' : 'trend'}>{trend} <small>vs last month</small></span></div>
      <div className={`stat-icon ${tone}`}><Icon name={icon} /></div>
    </article>
  )
}

export default StatCard
