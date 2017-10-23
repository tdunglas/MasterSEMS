import sun.security.util.Length


object tp {

	val x = 12;

	def sort_imp(xs: Array[Int]) {
		def swap(i: Int, j: Int) {
			val t = xs(i); xs(i) = xs(j); xs(j) = t
		}
		def sort1(l: Int, r: Int) {
			val pivot = xs((l + r) / 2)
					var i = l; var j = r
					while (i <= j) {
						while (xs(i) < pivot) i += 1
								while (xs(j) > pivot) j -= 1
								if (i <= j) {
									swap(i, j)
									i += 1
									j -= 1
								}
					}
					if (l < j) sort1(l, j)
					if (j < r) sort1(i, r)
		}
		sort1(0, xs.length - 1)
	}

	def sort(xs: Vector[Int]): Vector[Int] = {
			if (xs.length <= 1) xs
			else {
				val pivot = xs(xs.length / 2)
						Vector.concat(
								sort(xs.filter(x => pivot > x)),
								xs.filter(x => pivot == x),
								sort(xs.filter(x => pivot < x)))
			}
	}

	def clip(s: String) = s.substring(0, s.length - 1);

	def middle(s: String) = {
	     if(s.length() == 0)
	          s
	     else
	          s.substring(s.length/2, s.length/2+1);
	}

	def dtrunc(s:String) = s.substring(1, s.length - 1);

	def switch(s:String) = s.substring((s.length)/2, s.length) ++ s.substring(0, (s.length)/2);

	def dubmid(s:String) = {
          s.substring(0, s.length/2) ++ 
          middle(s)++
          s.substring(s.length/2, s.length) ;
     }
}