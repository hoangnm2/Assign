import java.util.LinkedList;

/**
 * Library class represent for one Library in the city
 * @author Hoang Nguyen Minh
 *
 * @param <T>
 */
public class Library<T> implements MaxTagValue {

	String libraryName;
	LinkedList<T> books;

	/**
	 * Constructor for Library
	 * @param libraryName
	 */
	public Library(String libraryName) {
		this.libraryName = libraryName;
		books = new LinkedList<T>();
	}

	/**
	 * Calculate the greatest value tag of all the books from a library
	 * @return the greatest value tag of all the books from a library
	 */
	public int findMaximumValueTag() {
		int maxElement = -100;
		for (int i = 0; i < books.size(); i++) {
			final Book book = (Book) books.get(i);
			if (book.getValueTag() > maxElement) {
				maxElement = book.getValueTag();
			}
		}

		return maxElement;
	}

	/**
	 * Rent request
	 * 
	 * @param wanted
	 * @param requestDate
	 * @param dueDate
	 * @return true/false
	 */
	public boolean rentRequest(Book wanted, String requestDate, String dueDate) {

		// Validate request book
		if (wanted == null || wanted.getBookName() == null) {
			System.err.println("Requested book is invalid.");
			return false;
		}
				
		try {
			Helper.checkDate(requestDate);
			Helper.checkDate(dueDate);
		} catch (DateFormatException e) {
			System.out.println(wanted + e.getMessage());
			return false;
		}

		for (int i = 0; i < books.size(); i++) {
			final Book book = (Book) books.get(i);
			// In case library owned the book
			if (book != null && wanted.getBookName().equalsIgnoreCase(book.getBookName())) {
				boolean isRented = book.rentBook(requestDate, dueDate, this);
				if (isRented) {
					System.out.println(book + " is rented successfully.");
				} else {
					System.out.println(book + " is not available and cant not be rented.");
				}
				return isRented;
			}
		}

		return false;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public LinkedList<T> getBooks() {
		return books;
	}

	/**
	 * Add a book to library
	 * @param book
	 */
	public void addBook(T book) {
		this.books.add(book);
	}

	/**
	 * To edit a book in library at current index
	 * @param index
	 * @param book
	 */
	public void editBook(int index, T book) {
		books.set(index, book);
	}

	/**
	 * Override hashcode function for Library
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result + ((libraryName == null) ? 0 : libraryName.hashCode());
		return result;
	}

	/**
	 * Override equals function for Library
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Library other = (Library) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		if (libraryName == null) {
			if (other.libraryName != null)
				return false;
		} else if (!libraryName.equals(other.libraryName))
			return false;
		return true;
	}

	/**
	 * To String method for library
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Library = ").append(libraryName).append("\n").append("[\n");
		for (int i = 0; i < books.size(); i++) {
			final Book bk = (Book) books.get(i);
			sb.append(bk).append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}

}
