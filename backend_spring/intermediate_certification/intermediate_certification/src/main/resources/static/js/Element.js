Element.prototype.getChildById = (ui_id)=>{
  for(const child of this.children)
    if(child.id == ui_id)
      return child;
  for(const child of this.children) {
    const to_return = child.getChildById(ui_id);
    if(to_return)
      return to_return;
  }
}

Element.prototype.getChildrenById = (ui_id)=>{
  const to_return = [];
  for(const child of this.children)
    if(child.id == ui_id)
      to_return.push(child);
  for(const child of this.children)
    to_return.concat(child.getChildrenById(ui_id));
  return to_return;
}

Element.prototype.getChildByName = function(s_name) {
  for(const child of this.children)
    if(child.name == s_name)
      return child;
  for(const child of this.children) {
    const to_return = child.getChildByName(s_name);
    if(to_return)
      return to_return;
  }
}
