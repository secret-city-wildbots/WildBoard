import { h } from 'preact';
import { useState } from 'preact/hooks';
import Switch from '../components/Switch';

interface SwitchItem {
  label: string;
  initialState?: boolean;
  disabled?: boolean;
}

interface SwitchGridProps {
  switches: SwitchItem[];
  columns?: number;
  onChange?: (id: number, state: boolean) => void;
  className?: string;
}

const SwitchGrid = ({
  switches,
  columns = 3,
  onChange,
  className = ''
}: SwitchGridProps) => {
  const [states, setStates] = useState<Record<number, boolean>>(
    Object.fromEntries(
      switches.map((s, i:number) => [i, s.initialState ?? false])
    )
  );

  const handleToggle = (id: number, state: boolean) => {
    setStates(prev => ({
      ...prev,
      [id]: state
    }));

    onChange?.(id, state);
  };

  return (
    <div
      className={`switch-grid ${className}`}
      style={{ gridTemplateColumns: `repeat(${columns}, 1fr)` }}
    >
      {switches.map((sw, i) => (
        <div
          key={i}
          className="switch-grid-item"
        >
          <label className="label-small switch-grid-label">
            {sw.label}
          </label>

          <Switch
            offColor='rgb(116,255,6)'
            onColor="#ef0001"
            initialState={states[i]}
            disabled={sw.disabled}
            onToggle={(state) => handleToggle(i, state)}
          />
        </div>
      ))}
    </div>
  );
};

export default SwitchGrid;