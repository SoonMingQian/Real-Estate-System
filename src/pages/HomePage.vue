<template>

  <div class="home-page">
    <main>
      <section class="hero">
        <h2>Welcome to Estate Eagle</h2>
        <p>Your one-stop solution for real estate needs.</p>
        <a href="/property-for-sale" class="cta">Browse Properties</a>
      </section>
      <h2 class="sec">Why Choose Us?</h2>   
      <section class="features">
        
        <div class="feature">
          <h3>Wide Selection</h3>
          <p>We offer a wide selection of properties to suit your needs.</p>
        </div>
        <div class="feature">
          <h3>Trusted Agents</h3>
          <p>Our agents are experienced and trustworthy.</p>
        </div>
        <div class="feature">
          <h3>Customer Service</h3>
          <p>We pride ourselves on our excellent customer service.</p>
        </div>
      </section>
    </main>

    <footer class="footer">
      <p>&copy; 2022 Estate Eagle</p>
    </footer>
  </div>
</template>

<script>

export default{
    name: 'HomePage',
    
}


</script>

<style scoped>
.home-page {
  font-family: Arial, sans-serif;
  color: #333;
  max-width: 1200px;
  margin: 0 auto;
}

.hero {
  background-image: url('../assets/background.jpg');
  background-size: cover;
  padding: 50px;
  color: #fff;
  text-align: center;
}

.hero h2 {
  font-size: 2.5em;
}

.hero p {
  font-size: 1.2em;
}

.hero .cta {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007BFF;
  color: #fff;
  text-decoration: none;
  border-radius: 5px;
}

.sec{
  display: flex;
  justify-content: space-around;
  padding: 30px;
  text-align: center;
}

.features {
  display: flex;
  justify-content: space-around;
  padding: 50px;
  text-align: center;
}

.features .feature h3 {
  font-size: 1.5em;
  margin-bottom: 10px;
}

.features .feature p {
  font-size: 1em;
}

.footer {
  background-color: #333;
  color: #fff;
  text-align: center;
  padding: 20px;
}
</style>