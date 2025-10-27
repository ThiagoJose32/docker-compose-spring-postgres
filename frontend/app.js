const tbody = document.getElementById('foods-tbody');
const listStatus = document.getElementById('list-status');
const form = document.getElementById('food-form');
const formStatus = document.getElementById('form-status');

function escapeHtml(v) {
  return String(v ?? '')
    .replaceAll('&', '&amp;').replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;').replaceAll('"', '&quot;')
    .replaceAll("'", '&#039;');
}

function formatDateIsoToBR(iso) {
  if (!iso) return '';
  const [y, m, d] = iso.split('-');
  if (!y || !m || !d) return iso;
  return `${d}/${m}/${y}`;
}

function rowTemplateRead(f) {
  const tr = document.createElement('tr');
  tr.dataset.mode = 'read';
  tr.dataset.id = f.id;
  tr.dataset.name = f.name ?? '';
  tr.dataset.expirationDate = f.expirationDate ?? '';
  tr.dataset.quantity = String(f.quantity ?? '');

  tr.innerHTML = `
    <td>${escapeHtml(f.id)}</td>
    <td>${escapeHtml(f.name)}</td>
    <td>${escapeHtml(formatDateIsoToBR(f.expirationDate))}</td>
    <td>${escapeHtml(f.quantity)}</td>
    <td class="actions">
      <button class="secondary btn-edit">Editar</button>
      <button class="danger btn-delete">Excluir</button>
    </td>
  `;
  bindReadActions(tr);
  return tr;
}

function rowTemplateEdit(tr) {
  const id = tr.dataset.id;
  const name = tr.dataset.name;
  const iso = tr.dataset.expirationDate;
  const qty = tr.dataset.quantity;

  tr.dataset.mode = 'edit';
  tr.innerHTML = `
    <td>${escapeHtml(id)}</td>
    <td><input class="table-edit" type="text" value="${escapeHtml(name)}" data-field="name" /></td>
    <td><input class="table-edit" type="date" value="${escapeHtml(iso)}" data-field="expirationDate" /></td>
    <td><input class="table-edit" type="number" min="0" step="1" value="${escapeHtml(qty)}" data-field="quantity" /></td>
    <td class="actions">
      <button class="btn-save">Salvar</button>
      <button class="secondary btn-cancel">Cancelar</button>
      <button class="danger btn-delete">Excluir</button>
    </td>
  `;
  bindEditActions(tr);
}

function bindReadActions(tr) {
  const id = tr.dataset.id;
  tr.querySelector('.btn-edit').addEventListener('click', () => rowTemplateEdit(tr));
  tr.querySelector('.btn-delete').addEventListener('click', () => onDelete(id));
}

function bindEditActions(tr) {
  const id = tr.dataset.id;
  const inputs = tr.querySelectorAll('input.table-edit');

  tr.querySelector('.btn-save').addEventListener('click', async () => {
    // monta diff só com alterados
    const original = {
      name: tr.dataset.name,
      expirationDate: tr.dataset.expirationDate,
      quantity: tr.dataset.quantity ? Number(tr.dataset.quantity) : null,
    };

    const current = {};
    inputs.forEach(inp => {
      const field = inp.dataset.field;
      let val = inp.value;
      if (field === 'quantity') val = val === '' ? null : Number.parseInt(val, 10);
      current[field] = val;
    });

    const patch = {};
    if (current.name !== original.name) patch.name = current.name;
    if (current.expirationDate !== original.expirationDate) patch.expirationDate = current.expirationDate;
    if (current.quantity !== original.quantity) patch.quantity = current.quantity;

    // se nada mudou, apenas volta ao modo leitura
    if (Object.keys(patch).length === 0) {
      rowBackToRead(tr, { id, ...original });
      return;
    }

    try {
      const res = await fetch(`/api/food/${id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
        body: JSON.stringify(patch),
      });
      if (!res.ok) {
        const txt = await res.text();
        throw new Error(`Erro ao atualizar (${res.status}): ${txt}`);
      }
      const updated = await res.json();
      rowBackToRead(tr, updated); // re-render com dados do backend
    } catch (e) {
      alert(e.message || 'Erro ao atualizar.');
    }
  });

  tr.querySelector('.btn-cancel').addEventListener('click', () => {
    rowBackToRead(tr, {
      id,
      name: tr.dataset.name,
      expirationDate: tr.dataset.expirationDate,
      quantity: tr.dataset.quantity ? Number(tr.dataset.quantity) : null,
    });
  });

  tr.querySelector('.btn-delete').addEventListener('click', () => onDelete(id));
}

function rowBackToRead(tr, f) {
  // atualiza dataset com o novo estado
  tr.dataset.name = f.name ?? '';
  tr.dataset.expirationDate = f.expirationDate ?? '';
  tr.dataset.quantity = String(f.quantity ?? '');

  const newTr = rowTemplateRead(f);
  tr.replaceWith(newTr);
}

async function onDelete(id) {
  if (!confirm(`Excluir ID ${id}?`)) return;
  try {
    const r = await fetch(`/api/food/${id}`, { method: 'DELETE' });
    if (!r.ok) throw new Error(`Falha ao excluir (${r.status})`);
    await loadFoods();
  } catch (e) {
    alert(e.message || 'Erro ao excluir.');
  }
}

async function loadFoods() {
  listStatus.textContent = 'Carregando...';
  try {
    const res = await fetch('/api/food', { headers: { 'Accept': 'application/json' } });
    if (!res.ok) throw new Error(`Erro ao buscar: ${res.status}`);
    const data = await res.json();

    tbody.innerHTML = '';

    if (!Array.isArray(data) || data.length === 0) {
      listStatus.textContent = 'Nenhum registro.';
      return;
    }

    for (const f of data) {
      const tr = rowTemplateRead(f);
      tbody.appendChild(tr);
    }
    listStatus.textContent = '';
  } catch (err) {
    console.error(err);
    listStatus.textContent = err.message || 'Erro ao carregar.';
  }
}

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  formStatus.textContent = 'Salvando...';

  const payload = {
    name: form.name.value.trim(),
    expirationDate: form.expirationDate.value,
    quantity: Number.parseInt(form.quantity.value, 10),
  };

  if (!payload.name) { formStatus.textContent = 'Informe o nome.'; return; }
  if (!payload.expirationDate) { formStatus.textContent = 'Informe a validade.'; return; }
  if (!Number.isFinite(payload.quantity) || payload.quantity < 0) {
    formStatus.textContent = 'Quantidade inválida.'; return;
  }

  try {
    const res = await fetch('/api/food', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
      body: JSON.stringify(payload),
    });
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(`Erro ao salvar (${res.status}): ${txt}`);
    }
    form.reset();
    formStatus.textContent = 'Salvo!';
    await loadFoods();
    setTimeout(() => (formStatus.textContent = ''), 1500);
  } catch (err) {
    console.error(err);
    formStatus.textContent = err.message || 'Erro ao salvar.';
  }
});

// Inicializa
loadFoods();
