import './Products.css';
import Card from "./Products/Card";

export default (props)=>{
  return (
    <main>
      <div className="Products">
        {props.products.map((product)=>{
          return <Card title={product.title} price={product.price} image={'/images/products/' + product.image}/>;
        })}
      </div>
    </main>
  );
};