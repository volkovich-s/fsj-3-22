import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './fonts/IMFellFrenchCanon-Regular.ttf';
import './fonts/IMFellFrenchCanon-Italic.ttf';
import {useState, useEffect} from 'react';
import Header from './components/Header';
import Banner from './components/Banner';
import Products from './components/Products';
import Footer from './components/Footer';

export default ()=>{
  const [products, setProducts] = useState([]);
  useEffect(()=>{
    fetch("https://6445159b914c816083c58826.mockapi.io/products").then((response)=>{
      return response.json();
    }).then((json)=>{
      setProducts(json);
    });
  }, []);
  return (
    <div className="App">
      <Header/>
      <Banner/>
      <Products products={products}/>
      <Footer/>
    </div>
  );
};