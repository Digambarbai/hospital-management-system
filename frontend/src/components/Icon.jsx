const icons = {
  dashboard: '▦', patients: '♙', appointments: '◷', doctors: '♧', nurses: '✚', departments: '▤',
  wards: '▥', reports: '◫', billing: '◇', settings: '⚙', help: '?', search: '⌕',
  bell: '♢', menu: '☰', calendar: '□', arrow: '→', plus: '+', close: '×', more: '•••',
}

function Icon({ name, className = '' }) {
  return <span aria-hidden="true" className={`icon ${className}`}>{icons[name] ?? '•'}</span>
}

export default Icon
