/**
 * The Book class implements Book
 *
 * @author Hoang Nguyen Minh
 */
class Book {

	String bookName; // the book name
	int valueTag; // an integer between -100 and 100
	Library<Book> library; // the library having this book it its inventory
	RentSettings rs; // rent settings

	/**
	 * Constructor for Book
	 * @param bookName
	 * @param valueTag
	 */
	public Book(String bookName, int valueTag) {
		this.bookName = bookName;
		this.valueTag = valueTag;
	}

	/**
	 * Set the rent dates; if dates are not valid catch DateFormatException and
	 * return false, if rentDate > dueDate catch RentPeriodException and return false
	 * if one the exceptions occur there is no RentSettings object
	 * @param rentDate
	 * @param dueDate
	 * @param library
	 * @return
	 */
	public boolean rentBook(String rentDate, String dueDate, Library<?> library) {
		// Validate: return false if rentDate or dueDate are not valid
		if (!Helper.isValidDate(rentDate) || !Helper.isValidDate(dueDate)) {
			System.err.println("Rent date or Due date is not valid.");
			return false;
		}

		// Check rentDate is available. Confirmed with prof, only check if
		// rentDate is from available date.
		if (rs != null) {
			try {
				if (Helper.timeDifference(rentDate, availableDate(library)) >= 0) {
					return false;
				}
			} catch (DateFormatException e) {
				e.printStackTrace();
			}
		}

		// Validate: if rentDate > dueDate catch RentPeriodException and return
		// false
		RentSettings rs;
		try {
			rs = this.new RentSettings(rentDate, dueDate, library);
			rs.borrowed = true;
		} catch (RentPeriodException | DateFormatException e) {
			return false;
		}

		this.rs = rs;
		return true;
	}

	/**
	 * Destroy the RentSettings object for this book
	 * @param library
	 */
	public void returnBook(Library<Book> library) {

		// Validate to check if library contains any book
		if (library.getBooks() == null || library.getBooks().size() == 0) {
			System.err.println("Bad request! Library must have atleast one book.");
		}

		for (int i = 0; i < library.getBooks().size(); i++) {
			final Book bk = (Book) library.getBooks().get(i);
			if (bk != null && bookName.equalsIgnoreCase(bk.getBookName())) {
				bk.rs = null;
				library.editBook(i, bk);
				System.out.println(
						"Book " + bk + " in library " + library.getLibraryName() + " have been returned successfully");
				return;
			}
		}
	}

	/**
	 * Return the date when this book is available
	 * 
	 * @param library
	 * @return next available date
	 */
	public String availableDate(Library<?> library) {
		for (int i = 0; i < library.getBooks().size(); i++) {
			final Book book = (Book) library.getBooks().get(i);
			if (book != null && book.getBookName() != null && book.getBookName().equals(bookName)) {
				// If rent settings is not set, return current date
				if (book.rs == null) {
					return Helper.getCurrentDate();
				} else {
					// return next day
					return Helper.getNextDate(book.rs.dueDate);
				}
			}
		}

		// Book doesnot exist
		return null;
	}

	/**
	 * Check if rent time is overlap with previous rent settings
	 * @param rentDate
	 * @param dueDate
	 * @return boolean
	 */
	public boolean isRentTimeAvailable(String rentDate, String dueDate) {
		try {
			if (!(Helper.timeDifference(rentDate, rs.dueDate) > 0 && Helper.timeDifference(dueDate, rs.rentDate) < 0)) {
				return true;
			}
		} catch (DateFormatException e) {
			System.out.println("Error when checking if rent time is available.");
		}
		return false;
	}

	/**
	 * Check if book is overdue
	 * 
	 * @return true if the current date is greater than the due date
	 */
	public boolean isBookOverdue() {
		if (rs == null) {
			return false;
		}
		String currentdate = Helper.getCurrentDate();
		try {
			if (Helper.timeDifference(rs.dueDate, currentdate) > 0) {
				return true;
			}
		} catch (DateFormatException e) {
			return false;
		}

		return false;
	}

	/**
	 * Check if a book is rented or not
	 * @param l
	 * @return boolean
	 */
	public boolean isRented(Library<Book> l) {
		// The book has been rented and not yet returned
		for (int i = 0; i < l.getBooks().size(); i++) {
			final Book book = (Book) l.getBooks().get(i);
			if (book == null || !book.getBookName().equals(this.getBookName())) {
				continue;
			}
			if (book.rs == null) {
				return false;
			} else {
				if (book.rs.borrowed) {
					return true;
				}
			}
		}
		return false;
	}

	public RentSettings getRs() {
		return rs;
	}

	public void setRs(RentSettings rs) {
		this.rs = rs;
	}

	public String bookName() {
		return "(" + bookName + ", " + valueTag + ')';
	}

	public String getBookName() {
		return bookName;
	}

	public int getValueTag() {
		return valueTag;
	}

	public Library<Book> getLibrary() {
		return library;
	}

	public void setLibrary(Library<Book> library) {
		this.library = library;
	}
	
	/**
	 * Override hashcode for Book object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + ((library == null) ? 0 : library.hashCode());
		result = prime * result + valueTag;
		return result;
	}

	/**
	 * Override hashcode for Book object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		if (library == null) {
			if (other.library != null)
				return false;
		} else if (!library.equals(other.library))
			return false;
		if (valueTag != other.valueTag)
			return false;
		return true;
	}

	/**
	 * To String method for Book
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("(").append(bookName).append(", ").append(valueTag);
		if (library != null) {
			sb.append(" => ").append(library.getLibraryName()).append(")");
			if (rs != null) {
				sb.append(" ").append(rs);
			}
		} else {
			sb.append(")");
		}
		return sb.toString();
	}

	/**
	 * The sub class RentSettings
	 *
	 * @author Hoang Nguyen Minh
	 */
	private class RentSettings {

		private String rentDate; // date when the item is requested
		private String dueDate; // date when the item must be returned
		private boolean borrowed = false; // true if the item is rented

		private Library<?> library;

		/**
		 * Constructor for RentSettings
		 * 
		 * @param rentDate
		 * @param dueDate
		 * @param library
		 * @throws DateFormatException
		 * @throws RentPeriodException
		 */
		private RentSettings(String rentDate, String dueDate, Library<?> library)
				throws DateFormatException, RentPeriodException {

			// Validate: if rentDate > dueDate catch RentPeriodException and
			// return false
			if (Helper.timeDifference(rentDate, dueDate) < 0) {
				throw new RentPeriodException("Due date must be after Rent date.");
			}

			this.rentDate = rentDate;
			this.dueDate = dueDate;
			this.library = library;

		}

		/**
		 * To String method for RentSettings
		 */
		@Override
		public String toString() {
			return "RentSettings (" + rentDate + ", " + dueDate + ", " + library.getLibraryName() + ", " + borrowed
					+ ")";
		}

	}
}
