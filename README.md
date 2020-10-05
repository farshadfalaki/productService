# productService
will run at port 8080 on host, swagger url http://localhost:8080/swagger-ui.html

I assume the product list csv file doesn't change frequently. I used caching for it which expires daily automatically.
But there is an endpoit to evict cach manually and immediately.
